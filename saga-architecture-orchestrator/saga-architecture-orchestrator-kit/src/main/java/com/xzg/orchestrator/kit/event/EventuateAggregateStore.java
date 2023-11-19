//package com.xzg.orchestrator.kit.event;
//
//import io.github.resilience4j.core.metrics.Snapshot;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.CompletableFuture;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event
// * @className: EventuateAggregateStore
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 12:30
// * @version: 1.0
// */
//public interface EventuateAggregateStore {
//
//    /**
//     * Save an aggregate in the event store
//     *
//     * @param clasz the class of the aggregate
//     * @param events the events to save
//     * @param <T> the type of the aggregate
//     * @return the id and version of the newly saved aggregate
//     */
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> save(Class<T> clasz, List<Event> events);
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> save(Class<T> clasz, List<Event> events, SaveOptions saveOptions);
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> save(Class<T> clasz, List<Event> events, Optional<SaveOptions> saveOptions);
//
//    <T extends Aggregate<T>> CompletableFuture<EntityWithMetadata<T>> find(Class<T> clasz, String entityId);
//    <T extends Aggregate<T>> CompletableFuture<EntityWithMetadata<T>> find(Class<T> clasz, String entityId, FindOptions findOptions);
//    <T extends Aggregate<T>> CompletableFuture<EntityWithMetadata<T>> find(Class<T> clasz, String entityId, Optional<FindOptions> findOptions);
//
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> update(Class<T> clasz, EntityIdAndVersion entityIdAndVersion, List<Event> events);
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> update(Class<T> clasz, EntityIdAndVersion entityIdAndVersion, List<Event> events, UpdateOptions updateOptions);
//    <T extends Aggregate<T>> CompletableFuture<EntityIdAndVersion> update(Class<T> clasz, EntityIdAndVersion entityIdAndVersion, List<Event> events, Optional<UpdateOptions> updateOptions);
//
//    CompletableFuture<?> subscribe(String subscriberId, Map<String, Set<String>> aggregatesAndEvents, SubscriberOptions subscriberOptions, Function<DispatchedEvent<Event>, CompletableFuture<?>> dispatch);
//
//    /**
//     * Possibly generate a snapshot
//     *
//     * @param aggregate - the updated aggregate
//     * @param snapshotVersion - the version of the snapshot, if any, that the aggregate was created from
//     * @param oldEvents - the old events that were used to recreate the aggregate
//     * @param newEvents - the new events generated as a result of executing a command
//     * @return an optional snapshot
//     */
//    Optional<Snapshot> possiblySnapshot(Aggregate aggregate, Optional<Integer> snapshotVersion, List<EventWithMetadata> oldEvents, List<Event> newEvents);
//
//    /**
//     * Recreate an aggregate from a snapshot
//     *
//     * @param clasz the aggregate class
//     * @param snapshot the snapshot
//     * @return the aggregate
//     */
//    Aggregate recreateFromSnapshot(Class<?> clasz, Snapshot snapshot);
//}
//
//
//