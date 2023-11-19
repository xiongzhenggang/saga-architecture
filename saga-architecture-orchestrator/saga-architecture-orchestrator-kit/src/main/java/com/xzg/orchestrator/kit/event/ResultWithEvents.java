package com.xzg.orchestrator.kit.event;

import java.util.Arrays;
import java.util.List;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: ResultWithEvents
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:23
 * @version: 1.0
 */
public class ResultWithEvents<T> {

    public final T result;
    public final List<DomainEvent> events;

    public ResultWithEvents(T result, List<DomainEvent> events) {
        this.result = result;
        this.events = events;
    }

    public ResultWithEvents(T result, DomainEvent... events) {
        this.result = result;
        this.events = Arrays.asList(events);
    }
}

    