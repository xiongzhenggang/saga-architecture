package com.xzg.orchestrator.kit.event;

import lombok.Data;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: DispatchedEvent
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:30
 * @version: 1.0
 */
@Data
public class DispatchedEvent<T> {
    private Event event;
}


    