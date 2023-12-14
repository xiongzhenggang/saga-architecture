package com.xzg.library.config.infrastructure.configuration;

import com.xzg.library.config.infrastructure.common.exception.UnauthorizedException;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity handleCustomException(HttpServletRequest request, UnauthorizedException e) {
        return new ResponseEntity<>(CommonResponse.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
