package com.xzg.orchestrator.kit.common.lock;

import java.sql.SQLException;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateSqlException
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:48
 * @version: 1.0
 */
public class EventuateSqlException extends RuntimeException {

    public EventuateSqlException(String message) {
        super(message);
    }

    public EventuateSqlException(SQLException e) {
        super(e);
    }

    public EventuateSqlException(Throwable t) {
        super(t);
    }
}


    