package com.xzg.orchestrator.kit.message.consumer.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaMessageConsumer
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:13
 * @version: 1.0
 */
public interface KafkaMessageConsumer {

    void assign(Collection<TopicPartition> topicPartitions);

    void seekToEnd(Collection<TopicPartition> topicPartitions);

    long position(TopicPartition topicPartition);

    void seek(TopicPartition topicPartition, long position);

    void subscribe(List<String> topics);

    void commitOffsets(Map<TopicPartition, OffsetAndMetadata> offsets);

    List<PartitionInfo> partitionsFor(String topic);

    ConsumerRecords<String, byte[]> poll(Duration duration);

    void pause(Set<TopicPartition> partitions);

    void resume(Set<TopicPartition> partitions);

    void close();

    void close(Duration duration);
}


    