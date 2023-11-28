package com.xzg.orchestrator.kit.event.consumer.kafka;

import com.xzg.orchestrator.kit.event.consumer.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String id = UUID.randomUUID().toString();

    private List<EventuateKafkaConsumer> consumers = new ArrayList<>();
    private EventuateKafkaMultiMessageConverter eventuateKafkaMultiMessageConverter = new EventuateKafkaMultiMessageConverter();
    private final ConsumerFactory<String, byte[]> consumerFactory;

    public MessageConsumerKafkaImpl(ConsumerFactory<String, byte[]> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }
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
                        .map(KafkaMessage::new)
                        .forEach(kafkaMessageHandler);
            } else {
                kafkaMessageHandler.accept(new KafkaMessage(StringBinaryMessageEncoding.bytesToString(message.getPayload())));
            }
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

    public String getId() {
        return id;
    }
}
