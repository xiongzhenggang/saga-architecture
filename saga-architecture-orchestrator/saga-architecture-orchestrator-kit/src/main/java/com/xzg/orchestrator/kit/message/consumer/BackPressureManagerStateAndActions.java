package com.xzg.orchestrator.kit.message.consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureManagerStateAndActions
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:23
 * @version: 1.0
 */
public class BackPressureManagerStateAndActions {

    final BackPressureActions actions;
    final BackPressureManagerState state;

    public BackPressureManagerStateAndActions(BackPressureActions actions, BackPressureManagerState state) {
        this.actions = actions;
        this.state = state;
    }

    public BackPressureManagerStateAndActions(BackPressureManagerState state) {
        this(BackPressureActions.NONE, state);
    }

}
    