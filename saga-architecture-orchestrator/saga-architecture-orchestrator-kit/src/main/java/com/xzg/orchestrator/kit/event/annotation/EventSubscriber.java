//package com.xzg.orchestrator.kit.event.annio;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event.annio
// * @className: EventSubscriber
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 12:51
// * @version: 1.0
// */
//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface EventSubscriber {
//
//    String id() default "";
//    SubscriberDurability durability() default SubscriberDurability.DURABLE;
//    SubscriberInitialPosition readFrom() default SubscriberInitialPosition.BEGINNING;
//    boolean progressNotifications() default false;
//
//
//}
//
//
//