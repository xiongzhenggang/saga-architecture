package com.xzg.orchestrator.kit.event.consumer;

import org.apache.kafka.common.TopicPartition;

import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureManager
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:22
 * @version: 1.0
 */
public class BackPressureManager {

    private final BackPressureConfig backPressureConfig;
    private Set<TopicPartition> allTopicPartitions = new HashSet<>();

    private BackPressureManagerState state = new BackPressureManagerNormalState();

    public BackPressureManager(BackPressureConfig backPressureConfig) {
        this.backPressureConfig = backPressureConfig;
    }

    public BackPressureActions update(Set<TopicPartition> topicPartitions, int backlog) {
        allTopicPartitions.addAll(topicPartitions);
        BackPressureManagerStateAndActions stateAndActions = state.update(allTopicPartitions, backlog, backPressureConfig);
        this.state = stateAndActions.state;
        return stateAndActions.actions;
    }


}
    