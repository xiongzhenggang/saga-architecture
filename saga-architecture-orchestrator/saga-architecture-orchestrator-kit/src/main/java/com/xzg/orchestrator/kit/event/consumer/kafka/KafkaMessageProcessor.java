package com.xzg.orchestrator.kit.event.consumer.kafka;

import com.xzg.orchestrator.kit.event.consumer.MessageConsumerBacklog;
import com.xzg.orchestrator.kit.event.consumer.OffsetTracker;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaMessageProcessor
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:20
 * @version: 1.0
 */
public class KafkaMessageProcessor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String subscriberId;
    private EventuateKafkaConsumerMessageHandler handler;
    private OffsetTracker offsetTracker = new OffsetTracker();

    private BlockingQueue<ConsumerRecord<String, byte[]>> processedRecords = new LinkedBlockingQueue<>();
    private AtomicReference<KafkaMessageProcessorFailedException> failed = new AtomicReference<>();

    public KafkaMessageProcessor(String subscriberId, EventuateKafkaConsumerMessageHandler handler) {
        this.subscriberId = subscriberId;
        this.handler = handler;
    }

    private Set<MessageConsumerBacklog> consumerBacklogs = new HashSet<>();

    public void process(ConsumerRecord<String, byte[]> record) {
        throwFailureException();
        offsetTracker.noteUnprocessed(new TopicPartition(record.topic(), record.partition()), record.offset());
        MessageConsumerBacklog consumerBacklog = handler.apply(record, (result, t) -> {
            if (t != null) {
                logger.error("Got exception: ", t);
                failed.set(new KafkaMessageProcessorFailedException(t));
            } else {
                logger.debug("Adding processed record to queue {} {}", subscriberId, record.offset());
                processedRecords.add(record);
            }
        });
        if (consumerBacklog != null)
            consumerBacklogs.add(consumerBacklog);
    }

    void throwFailureException() {
        if (failed.get() != null)
            throw failed.get();
    }

    public Map<TopicPartition, OffsetAndMetadata> offsetsToCommit() {
        int count = 0;
        while (true) {
            ConsumerRecord<String, byte[]> record = processedRecords.poll();
            if (record == null)
                break;
            count++;
            offsetTracker.noteProcessed(new TopicPartition(record.topic(), record.partition()), record.offset());
        }
        logger.trace("removed {} {} processed records from queue", subscriberId, count);
        return offsetTracker.offsetsToCommit();
    }

    public void noteOffsetsCommitted(Map<TopicPartition, OffsetAndMetadata> offsetsToCommit) {
        offsetTracker.noteOffsetsCommitted(offsetsToCommit);
    }

    public OffsetTracker getPending() {
        return offsetTracker;
    }

    public int backlog() {
        return consumerBacklogs.stream().mapToInt(MessageConsumerBacklog::size).sum();
    }

}


    