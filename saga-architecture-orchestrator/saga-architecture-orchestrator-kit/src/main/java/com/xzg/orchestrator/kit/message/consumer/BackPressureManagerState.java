package com.xzg.orchestrator.kit.message.consumer;

import org.apache.kafka.common.TopicPartition;

import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureManagerState
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:23
 * @version: 1.0
 */
public interface BackPressureManagerState {
    BackPressureManagerStateAndActions update(Set<TopicPartition> allTopicPartitions, int backlog, BackPressureConfig backPressureConfig);
}
    