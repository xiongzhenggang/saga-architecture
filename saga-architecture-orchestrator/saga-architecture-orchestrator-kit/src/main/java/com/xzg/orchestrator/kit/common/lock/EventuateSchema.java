package com.xzg.orchestrator.kit.common.lock;

import org.apache.commons.lang.StringUtils;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateSchema
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:46
 * @version: 1.0
 */
public class EventuateSchema {
    public static final String DEFAULT_SCHEMA = "eventuate";
    public static final String EMPTY_SCHEMA = "none";

    private final String eventuateDatabaseSchema;

    public EventuateSchema() {
        eventuateDatabaseSchema = DEFAULT_SCHEMA;
    }

    public EventuateSchema(String eventuateDatabaseSchema) {
        this.eventuateDatabaseSchema = StringUtils.isEmpty(eventuateDatabaseSchema) ? DEFAULT_SCHEMA : eventuateDatabaseSchema;
    }

    public String getEventuateDatabaseSchema() {
        return eventuateDatabaseSchema;
    }

    public boolean isEmpty() {
        return EMPTY_SCHEMA.equals(eventuateDatabaseSchema);
    }

    public boolean isDefault() {
        return DEFAULT_SCHEMA.equals(eventuateDatabaseSchema);
    }

    public String qualifyTable(String table) {
        if (isEmpty()) return table;

        String schema = isDefault() ? DEFAULT_SCHEMA : eventuateDatabaseSchema;

        return String.format("%s.%s", schema, table);
    }
}

    