package com.xzg.orchestrator.kit.common.lock;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateRowMapper
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:47
 * @version: 1.0
 */
public interface EventuateRowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}


    