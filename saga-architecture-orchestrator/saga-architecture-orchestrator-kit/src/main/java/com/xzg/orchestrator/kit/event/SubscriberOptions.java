//package com.xzg.orchestrator.kit.event;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event
// * @className: SubscriberOptions
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 2:39
// * @version: 1.0
// */
//public class SubscriberOptions {
//
//    private SubscriberDurability durability;
//    private SubscriberInitialPosition readFrom;
//    private boolean progressNotifications;
//
//    public static SubscriberOptions DEFAULTS = new SubscriberOptions(SubscriberDurability.DURABLE, SubscriberInitialPosition.BEGINNING, false);
//
//    public SubscriberOptions() {
//    }
//
//    public SubscriberOptions(SubscriberDurability durability, SubscriberInitialPosition readFrom, boolean progressNotifications) {
//        this.durability = durability;
//        this.readFrom = readFrom;
//        this.progressNotifications = progressNotifications;
//    }
//
//    public SubscriberDurability getDurability() {
//        return durability;
//    }
//
//    public SubscriberInitialPosition getReadFrom() {
//        return readFrom;
//    }
//
//    public boolean isProgressNotifications() {
//        return progressNotifications;
//    }
//
//}
//
//
//