//package com.xzg.orchestrator.kit.event;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event
// * @className: EntityWithMetadata
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 12:52
// * @version: 1.0
// */
//public class EntityWithMetadata<T extends Aggregate> {
//
//    private EntityIdAndVersion entityIdAndVersion;
//    private Optional<Long> snapshotVersion;
//    private List<EventWithMetadata> events;
//
//    public EntityWithMetadata(EntityIdAndVersion entityIdAndVersion, Optional<Long> snapshotVersion, List<EventWithMetadata> events, T entity) {
//        this.entityIdAndVersion = entityIdAndVersion;
//        this.snapshotVersion = snapshotVersion;
//        this.events = events;
//        this.entity = entity;
//    }
//
//    private T entity;
//
//    public T getEntity() {
//        return entity;
//    }
//
//    public EntityIdAndVersion getEntityIdAndVersion() {
//        return entityIdAndVersion;
//    }
//
//    public Optional<Long> getSnapshotVersion() {
//        return snapshotVersion;
//    }
//
//    public EntityWithIdAndVersion<T> toEntityWithIdAndVersion() {
//        return new EntityWithIdAndVersion<T>(entityIdAndVersion, entity);
//    }
//
//    public List<EventWithMetadata> getEvents() {
//        return events;
//    }
//}
//
//