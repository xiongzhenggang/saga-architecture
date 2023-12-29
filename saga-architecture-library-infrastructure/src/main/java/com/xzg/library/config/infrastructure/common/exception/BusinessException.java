package com.xzg.library.config.infrastructure.common.exception;

import lombok.Builder;
import lombok.Data;


/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: BusinessException
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
 * 12/29/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/29/2023
 * </p>
 */
@Builder
@Data
public class BusinessException extends  RuntimeException  {
    private String message;

}
