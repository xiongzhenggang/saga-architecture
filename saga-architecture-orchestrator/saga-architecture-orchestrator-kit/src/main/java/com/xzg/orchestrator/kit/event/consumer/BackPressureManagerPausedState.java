package com.xzg.orchestrator.kit.event.consumer;

import org.apache.kafka.common.TopicPartition;

import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureManagerPausedState
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:24
 * @version: 1.0
 */
public class BackPressureManagerPausedState implements BackPressureManagerState {

    private Set<TopicPartition> suspendedPartitions;

    public BackPressureManagerPausedState(Set<TopicPartition> pausedTopic) {
        this.suspendedPartitions = new HashSet<>(pausedTopic);
    }

    public static BackPressureManagerStateAndActions transitionTo(Set<TopicPartition> allTopicPartitions) {
        return new BackPressureManagerStateAndActions(BackPressureActions.pause(allTopicPartitions), new BackPressureManagerPausedState(allTopicPartitions));
    }


    @Override
    public BackPressureManagerStateAndActions update(Set<TopicPartition> allTopicPartitions, int backlog, BackPressureConfig backPressureConfig) {
        if (backlog <= backPressureConfig.getLow()) {
            return BackPressureManagerNormalState.transitionTo(suspendedPartitions);
        } else {
            Set<TopicPartition> toSuspend = new HashSet<>(allTopicPartitions);
            toSuspend.removeAll(suspendedPartitions);
            suspendedPartitions.addAll(toSuspend);
            return new BackPressureManagerStateAndActions(BackPressureActions.pause(toSuspend), this);
        }
    }
}

    