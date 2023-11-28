package com.xzg.orchestrator.kit.event.consumer.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;

import java.time.Duration;
import java.util.*;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: DefaultKafkaMessageConsumer
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:41
 * @version: 1.0
 */
public class DefaultKafkaMessageConsumer implements KafkaMessageConsumer {

    private final Consumer<String, byte[]> delegate;

    public static KafkaMessageConsumer create(ConsumerFactory<String, byte[]> consumerFactory) {
        return new DefaultKafkaMessageConsumer(consumerFactory.createConsumer());
    }

    private DefaultKafkaMessageConsumer(Consumer<String, byte[]> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void assign(Collection<TopicPartition> topicPartitions) {
        delegate.assign(topicPartitions);
    }

    @Override
    public void seekToEnd(Collection<TopicPartition> topicPartitions) {
        delegate.seekToEnd(topicPartitions);
    }

    @Override
    public long position(TopicPartition topicPartition) {
        return delegate.position(topicPartition);
    }

    @Override
    public void seek(TopicPartition topicPartition, long position) {
        delegate.seek(topicPartition, position);
    }

    @Override
    public void subscribe(List<String> topics) {
        delegate.subscribe(new ArrayList<>(topics));
    }

    @Override
    public void commitOffsets(Map<TopicPartition, OffsetAndMetadata> offsets) {
        delegate.commitSync(offsets);
    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic) {
        return delegate.partitionsFor(topic);
    }

    @Override
    public ConsumerRecords<String, byte[]> poll(Duration duration) {
        return delegate.poll(duration);
    }

    @Override
    public void pause(Set<TopicPartition> partitions) {
        delegate.pause(partitions);
    }

    @Override
    public void resume(Set<TopicPartition> partitions) {
        delegate.resume(partitions);
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public void close(Duration duration) {
        delegate.close(duration);
    }
}

    