package com.xzg.orchestrator.kit.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: SwimlaneDispatcher
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:06
 * @version: 1.0
 */
public class SwimlaneDispatcher {

    private static Logger logger = LoggerFactory.getLogger(SwimlaneDispatcher.class);

    private String subscriberId;
    private Integer swimlane;
    private Executor executor;

    private final LinkedBlockingQueue<QueuedMessage> queue = new LinkedBlockingQueue<>();
    private AtomicBoolean running = new AtomicBoolean(false);

    private SwimlaneDispatcherBacklog consumerStatus = new SwimlaneDispatcherBacklog(queue);

    public SwimlaneDispatcher(String subscriberId, Integer swimlane, Executor executor) {
        this.subscriberId = subscriberId;
        this.swimlane = swimlane;
        this.executor = executor;
    }

    public boolean getRunning() {
        return running.get();
    }

    public SwimlaneDispatcherBacklog dispatch(RawKafkaMessage message, Consumer<RawKafkaMessage> messageConsumer) {
        synchronized (queue) {
            QueuedMessage queuedMessage = new QueuedMessage(message, messageConsumer);
            queue.add(queuedMessage);
            logger.trace("added message to queue: {} {} {}", subscriberId, swimlane, message);
            if (running.compareAndSet(false, true)) {
                logger.trace("Stopped - attempting to process newly queued message: {} {}", subscriberId, swimlane);
                processNextQueuedMessage();
            } else
                logger.trace("Running - Not attempting to process newly queued message: {} {}", subscriberId, swimlane);
        }
        return consumerStatus;
    }

    private void processNextQueuedMessage() {
        executor.execute(this::processQueuedMessage);
    }

    class QueuedMessage {
        RawKafkaMessage message;
        Consumer<RawKafkaMessage> messageConsumer;

        public QueuedMessage(RawKafkaMessage message, Consumer<RawKafkaMessage> messageConsumer) {
            this.message = message;
            this.messageConsumer = messageConsumer;
        }
    }


    public void processQueuedMessage() {
        while (true) {
            QueuedMessage queuedMessage = getNextMessage();
            if (queuedMessage == null) {
                logger.trace("No queued message for {} {}", subscriberId, swimlane);
                return;
            } else {
                logger.trace("Invoking handler for message for {} {} {}", subscriberId, swimlane, queuedMessage.message);
                try {
                    queuedMessage.messageConsumer.accept(queuedMessage.message);
                } catch (RuntimeException e) {
                    logger.error("Exception handling message - terminating", e);
                    return;
                }
            }
        }
    }

    private QueuedMessage getNextMessage() {
        QueuedMessage queuedMessage = queue.poll();
        if (queuedMessage != null)
            return queuedMessage;

        synchronized (queue) {
            queuedMessage = queue.poll();
            if (queuedMessage == null) {
                running.compareAndSet(true, false);
            }
            return queuedMessage;
        }
    }
}

    