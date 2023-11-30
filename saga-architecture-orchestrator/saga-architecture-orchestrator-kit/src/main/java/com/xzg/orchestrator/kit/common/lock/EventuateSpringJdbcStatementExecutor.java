package com.xzg.orchestrator.kit.common.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.lock
 * @className: EventuateSpringJdbcStatementExecutor
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:47
 * @version: 1.0
 */
public class EventuateSpringJdbcStatementExecutor implements EventuateJdbcStatementExecutor  {

    private JdbcTemplate jdbcTemplate;

    public EventuateSpringJdbcStatementExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public long insertAndReturnGeneratedId(String sql, String idColumn, Object... params) {
        if (logger.isDebugEnabled())
            logger.debug("insertAndReturnGeneratedId {} {} {}", sql, idColumn, Arrays.asList(params));
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                for (int i = 1; i <= params.length; i++) {
                    preparedStatement.setObject(i, params[i - 1]);
                }

                return preparedStatement;
            }, holder);

            if (holder.getKeys().size() > 1) {
                // necessary for postgres. For postgres holder returns all columns.
                return (Long)holder.getKeys().get(idColumn);
            } else {
                return  holder.getKey().longValue();
            }
        } catch (DuplicateKeyException e) {
            throw new EventuateDuplicateKeyException(e);
        }
    }

    @Override
    public int update(String sql, Object... params) {
        try {
            return jdbcTemplate.update(sql, params);
        } catch (DuplicateKeyException e) {
            throw new EventuateDuplicateKeyException(e);
        }
    }

    @Override
    public <T> List<T> query(String sql, EventuateRowMapper<T> eventuateRowMapper, Object... params) {
        return jdbcTemplate.query(sql, eventuateRowMapper::mapRow, params);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object... parameters) {
        return jdbcTemplate.queryForList(sql, parameters);
    }
}
    