package com.xzg.orchestrator.kit.event;

import lombok.Data;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: EntityWithIdAndVersion
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:52
 * @version: 1.0
 */
@Data
public class EntityWithIdAndVersion<T> {
    private final EntityIdAndVersion entityIdAndVersion;
    private final T aggregate;


    public EntityWithIdAndVersion(EntityIdAndVersion entityIdAndVersion, T aggregate) {

        this.entityIdAndVersion = entityIdAndVersion;
        this.aggregate = aggregate;
    }


}

    