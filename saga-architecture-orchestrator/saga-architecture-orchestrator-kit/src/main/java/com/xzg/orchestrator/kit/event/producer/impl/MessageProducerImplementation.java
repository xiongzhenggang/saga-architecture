//package com.xzg.orchestrator.kit.event.producer.impl;
//
//import com.xzg.orchestrator.kit.event.Message;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event.producer.impl
// * @className: MessageProducerImplementation
// * @author: xzg
// * @description: TODO
// * @date: 25/11/2023-下午 8:07
// * @version: 1.0
// */
//public interface MessageProducerImplementation {
//
//    void send(Message message);
//
//    default void setMessageIdIfNecessary(Message message) {
//        //do nothing by default
//    }
//
//    default void withContext(Runnable runnable) {
//        runnable.run();
//    }
//}
//
//