package com.xzg.orchestrator.kit.event;

import lombok.Data;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: EntityIdAndVersion
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:52
 * @version: 1.0
 */
@Data
public class EntityIdAndVersion {

    private final String entityId;
    private final Integer entityVersion;


    public EntityIdAndVersion(String entityId, Integer entityVersion) {
        this.entityId = entityId;
        this.entityVersion = entityVersion;
    }

}

    