package com.xzg.orchestrator.kit.common.lock;

import java.util.List;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateJdbcStatementExecutor
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:47
 * @version: 1.0
 */

public interface EventuateJdbcStatementExecutor {
    long insertAndReturnGeneratedId(String sql, String idColumn, Object... params);
    int update(String sql, Object... params);
    <T> List<T> query(String sql, EventuateRowMapper<T> eventuateRowMapper, Object... params);
    List<Map<String, Object>> queryForList(String sql, Object... parameters);
}

    