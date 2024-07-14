package com.xzg.library.config.infrastructure.configuration;

import com.xzg.library.config.infrastructure.common.exception.BusinessException;
import com.xzg.library.config.infrastructure.common.exception.UnauthorizedException;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author xiongzhenggang
 */
@ControllerAdvice(annotations = {EnableResponseBodyWrap.class})
@Component
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public CommonResponse handleAuthException(HttpServletRequest request, UnauthorizedException e) {
        log.error("请求失败，无权限");
        return CommonResponse.failure(HttpStatus.UNAUTHORIZED,e.getMessage());
    }
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public CommonResponse handleBusinessException(HttpServletRequest request, BusinessException e) {
        log.error("请求失败，业务失败");
        return CommonResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, Exception e) {
        log.error("请求失败");
        return CommonResponse.failure(e.getMessage());
    }
    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public CommonResponse handleUserException(HttpServletRequest request, UsernameNotFoundException e) {
        log.error("请求失败");
        return CommonResponse.failure(e.getMessage());
    }
}
