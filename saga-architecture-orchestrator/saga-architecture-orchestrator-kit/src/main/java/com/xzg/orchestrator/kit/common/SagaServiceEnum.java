package com.xzg.orchestrator.kit.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>
 * <b>项目名称： </b>:twoplatform
 * <b>Class name</b>: ApiSysCodeEnum
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
 * 11/27/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 11/27/2023
 * </p>
 */
public enum SagaServiceEnum {
    ACCOUNT_SERVICE("accountService"),
    ORDER_SERVICE("orderService"),
    ORCHESTRATOR_SERVICE("orchestratorService"),
    GOODS_SERVICE("goodsService");

    private String type;

    private SagaServiceEnum(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }

    @JsonCreator
    public static SagaServiceEnum create(String type) {
        SagaServiceEnum[] var1 = values();
        int var2 = var1.length;
        for(int var3 = 0; var3 < var2; ++var3) {
            SagaServiceEnum value = var1[var3];
            if (value.type.equalsIgnoreCase(type)) {
                return value;
            }
        }
        return null;
    }
}
