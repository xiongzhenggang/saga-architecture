package com.xzg.authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: AuthUserRole
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
 * 1/2/2024   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 1/2/2024
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="AUTH_USER_ROLE")
@Data
public class AuthUserRole extends BaseEntity{
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 创建人
     */
    private Long createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 更新人
     */
    private Long updatedBy;
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
