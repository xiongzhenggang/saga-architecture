package com.xzg.orchestrator.kit.event.consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: ConsumerCallbacks
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:16
 * @version: 1.0
 */
public interface ConsumerCallbacks {
    void onTryCommitCallback();
    void onCommitedCallback();
    void onCommitFailedCallback();
}


    