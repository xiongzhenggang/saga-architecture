package com.xzg.orchestrator.kit.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: SwimlaneBasedDispatcher
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:06
 * @version: 1.0
 */
public class SwimlaneBasedDispatcher {

    private static Logger logger = LoggerFactory.getLogger(SwimlaneBasedDispatcher.class);

    private final ConcurrentHashMap<Integer, SwimlaneDispatcher> map = new ConcurrentHashMap<>();
    private Executor executor;
    private String subscriberId;

    public SwimlaneBasedDispatcher(String subscriberId, Executor executor) {
        this.subscriberId = subscriberId;
        this.executor = executor;
    }

    public SwimlaneDispatcherBacklog dispatch(RawKafkaMessage message, Integer swimlane, Consumer<RawKafkaMessage> target) {
        SwimlaneDispatcher swimlaneDispatcher = getOrCreate(swimlane);
        return swimlaneDispatcher.dispatch(message, target);
    }

    private SwimlaneDispatcher getOrCreate(Integer swimlane) {
        SwimlaneDispatcher swimlaneDispatcher = map.get(swimlane);
        if (swimlaneDispatcher == null) {
            logger.trace("No dispatcher for {} {}. Attempting to create", subscriberId, swimlane);
            swimlaneDispatcher = new SwimlaneDispatcher(subscriberId, swimlane, executor);
            SwimlaneDispatcher r = map.putIfAbsent(swimlane, swimlaneDispatcher);
            if (r != null) {
                logger.trace("Using concurrently created SwimlaneDispatcher for {} {}", subscriberId, swimlane);
                swimlaneDispatcher = r;
            } else {
                logger.trace("Using newly created SwimlaneDispatcher for {} {}", subscriberId, swimlane);
            }
        }
        return swimlaneDispatcher;
    }
}

    