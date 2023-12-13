package com.xzg.library.config.infrastructure.configuration;

import com.xzg.library.config.infrastructure.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author xiongzhenggang
 */
@Component
@ControllerAdvice(annotations = {EnableResponseBodyWrap.class})
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice {

    public static final String JSON = "json";
    public static final String PLAIN = "plain";

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (mediaType.getSubtype().equals(JSON)){
            if (o instanceof CommonResponse<?> || o instanceof ResponseEntity){
                return o;
            }
            CommonResponse contentSet = CommonResponse.success(o);
            return contentSet;
        } else if (mediaType.getSubtype().equals(PLAIN) && o instanceof String) {
            return CommonResponse.success(o);
        } else {
            return o;
        }
    }
}