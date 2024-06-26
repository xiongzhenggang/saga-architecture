package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.message.MessageImpl;
import com.xzg.orchestrator.kit.message.consumer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MessageConsumerKafkaImpl
 * @author: xzg
 * @description: TODO
 * @date: 20/11/2023-下午 8:41
 * @version: 1.0
 */
@Slf4j
public class MessageConsumerKafkaImpl implements CommonMessageConsumer {

    private List<EventuateKafkaConsumer> consumers = new ArrayList<>();
    private EventuateKafkaMultiMessageConverter eventuateKafkaMultiMessageConverter = new EventuateKafkaMultiMessageConverter();
    private final ConsumerFactory<String, byte[]> consumerFactory;

    public MessageConsumerKafkaImpl(ConsumerFactory<String, byte[]> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    /**
     * 消息监听
     * @param subscriberId
     * @param channels
     * @param handler
     * @return
     */
    @Override
    public KafkaSubscription subscribe(String subscriberId, Set<String> channels, KafkaMessageHandler handler) {
        SwimlaneBasedDispatcher swimlaneBasedDispatcher = new SwimlaneBasedDispatcher(subscriberId, Executors.newCachedThreadPool());
        EventuateKafkaConsumerMessageHandler kcHandler = (record, callback) -> swimlaneBasedDispatcher.dispatch(new RawKafkaMessage(record.value()),
                record.partition(),
                message -> handle(message, callback, handler));
        EventuateKafkaConsumer kc = new EventuateKafkaConsumer(subscriberId,
                kcHandler,
                new ArrayList<>(channels),
                new BackPressureConfig(),
                consumerFactory);
        consumers.add(kc);
        kc.start();
        return new KafkaSubscription(() -> {
            kc.stop();
            consumers.remove(kc);
        });
    }

    public void handle(RawKafkaMessage message, BiConsumer<Void, Throwable> callback, KafkaMessageHandler kafkaMessageHandler) {
        try {
            if (eventuateKafkaMultiMessageConverter.isMultiMessage(message.getPayload())) {
                eventuateKafkaMultiMessageConverter
                        .convertBytesToMessages(message.getPayload())
                        .getMessages()
                        .stream()
                        .map(EventuateKafkaMultiMessage::getValue)
                        .map(MessageImpl::new)
                        .forEach(kafkaMessageHandler);
            } else {
                String jsonStr = StringBinaryMessageEncoding.bytesToString(message.getPayload());
                log.info("parser jsonStr=：{}",jsonStr);
                kafkaMessageHandler.accept(JsonUtil.jsonStr2obj(jsonStr,MessageImpl.class));
            }
            //@TODO SagaMessage check and save
            callback.accept(null, null);
        } catch (Throwable e) {
            callback.accept(null, e);
            throw e;
        }
    }

    @Override
    public void close() {
        consumers.forEach(EventuateKafkaConsumer::stop);
    }

}
