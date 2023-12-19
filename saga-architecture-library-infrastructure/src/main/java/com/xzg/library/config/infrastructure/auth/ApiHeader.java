package com.xzg.library.config.infrastructure.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 请求头拦截注解
 * @author xiongzhenggang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeader {

    /**
     * api header是否必须
     * @return boolean
     */
    boolean required() default true;
}