package com.xzg.orchestrator.kit.message.consumer;

import org.apache.kafka.common.TopicPartition;

import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureManagerNormalState
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:24
 * @version: 1.0
 */
public class BackPressureManagerNormalState implements BackPressureManagerState {

    public static BackPressureManagerStateAndActions transitionTo(Set<TopicPartition> suspendedPartitions) {
        return new BackPressureManagerStateAndActions(BackPressureActions.resume(suspendedPartitions), new BackPressureManagerNormalState());
    }

    @Override
    public BackPressureManagerStateAndActions update(Set<TopicPartition> allTopicPartitions, int backlog, BackPressureConfig backPressureConfig) {
        if (backlog > backPressureConfig.getHigh()) {
            return BackPressureManagerPausedState.transitionTo(allTopicPartitions);
        } else {
            return new BackPressureManagerStateAndActions(this);
        }
    }

}
    