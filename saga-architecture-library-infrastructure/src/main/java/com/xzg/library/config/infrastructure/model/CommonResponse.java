package com.xzg.library.config.infrastructure.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.model
 * @className: CommonResponse
 * @author: xzg
 * @description:
 * 为了简化编码而设计的HTTP Response对象包装类和工具集
 *   <p>
 *   带有服务状态编码的（带有Code字段的）JavaBean领域对象包装类
 *  Code字段的通常用于服务消费者判定该请求的业务处理是否成功。
 *   <p>
 *  统一约定：
 *  - 当服务调用正常完成，返回Code一律以0表示
 *   - 当服务调用产生异常，可自定义不为0的Code值，此时Message字段作为返回客户端的详细信息
 * @date: 11/11/2023-下午 2:57
 * @version: 1.0
 */
@Slf4j
@Builder
@Data
public class CommonResponse<T> {

    private Integer status;
    private List<String> messages;
    private T result;
    /**
     * 向客户端发送自定义操作信息
     */
    public static CommonResponse send(HttpStatus status, String message) {
        return CommonResponse.builder().messages(Collections.singletonList(message))
                .status(status.value()).build();
    }
    /**
     * 向客户端发送自定义操作信息
     */
    public static<T> CommonResponse send(HttpStatus status, String message,T result) {
        return CommonResponse.builder()
                .result(result)
                .messages(Collections.singletonList(message))
                .status(status.value()).build();
    }
    /**
     * 向客户端发送操作失败的信息
     */
    public static CommonResponse failure(String message) {
        return send(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
    /**
     * 向客户端发送操作失败状态
     */
    public static CommonResponse failure(HttpStatus status,String msg) {
        return send(status, msg);
    }
    /**
     * 向客户端发送操作成功的信息
     */
    public static <T> CommonResponse success(String message,T result) {
        return send(HttpStatus.OK, message,result);
    }

    /**
     * 向客户端发送操作成功的信息
     */
    public static CommonResponse success() {
        return send(HttpStatus.OK, "操作已成功");
    }

    /**
     * 向客户端发送操作成功的信息
     */
    public static <T> CommonResponse success(T result) {
        return send(HttpStatus.OK, "操作已成功",result);
    }

    /**
     * 执行操作，并根据操作是否成功返回给客户端相应信息
     * 封装了在服务端接口中很常见的执行操作，成功返回成功标志、失败返回失败标志的通用操作，用于简化编码
     */
    public static CommonResponse op(Runnable executor) {
        return op(executor, e -> log.error(e.getMessage(), e));
    }

    /**
     * 执行操作（带自定义的失败处理），并根据操作是否成功返回给客户端相应信息
     * 封装了在服务端接口中很常见的执行操作，成功返回成功标志、失败返回失败标志的通用操作，用于简化编码
     */
    public static CommonResponse op(Runnable executor, Consumer<Exception> exceptionConsumer) {
        try {
            executor.run();
            return CommonResponse.success();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
            return CommonResponse.failure(e.getMessage());
        }
    }

}
