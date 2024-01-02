package com.xzg.orchestrator.kit.message.consumer.model;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer.model
 * @className: MetaAttribute
 * @author: xzg
 * @description: TODO
 * @date: 25/11/2023-下午 5:03
 * @version: 1.0
 */
public enum MetaAttribute
{
    /**
     * The epoch or start of time. Default is 'UNIX' which is midnight January 1, 1970 UTC
     */
    EPOCH,

    /**
     * Time unit applied to the epoch. Can be second, millisecond, microsecond, or nanosecond.
     */
    TIME_UNIT,

    /**
     * The type relationship to a FIX tag value encoded type. For reference only.
     */
    SEMANTIC_TYPE,

    /**
     * Field presence indication. Can be optional, required, or constant.
     */
    PRESENCE
}


    