package com.xzg.orchestrator.kit.orchestration.saga.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: SagaMessage
 * </p>
 * <p>
 * <b>Class description</b>:
 *
 * </p>
 * <p>
 * <b>Author</b>: xiongzhenggang
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 1/3/2024   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 1/3/2024
 * </p>
 */
@Table(name="SAGA_MESSAGE")
@Data
@Entity
public class SagaMessage {
    /**
     * 租户号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息事件流水号：同一消息幂等
     */
    private String sagaId;
    /**
     * 消息事件流水号：同一消息幂等
     */
    private String serial;
    /**
     * 事件内容
     */
    @Column(name = "PAYLOAD", length = 1024)
    private String payload;
    /**
     * 数据来源
     * SEND/RECEIVE
     */
    private String source;
    /**
     * 事件消息类型
     */
    private String type;
    /**
     * 事件发送状态，发送中，已完成
     */
    private String sendStatus;
    /**
     * 消息头
     */
    @Column(name = "HEADERS", length = 1024)
    private String headers;
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 创建人
     */
    private String createdBy;
}
