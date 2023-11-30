package com.xzg.orchestrator.kit.common.lock;

import java.sql.SQLException;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateDuplicateKeyException
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:48
 * @version: 1.0
 */
public class EventuateDuplicateKeyException extends EventuateSqlException {
    public EventuateDuplicateKeyException(SQLException sqlException) {
        super(sqlException);
    }

    public EventuateDuplicateKeyException(Throwable throwable) {
        super(throwable);
    }
}


    