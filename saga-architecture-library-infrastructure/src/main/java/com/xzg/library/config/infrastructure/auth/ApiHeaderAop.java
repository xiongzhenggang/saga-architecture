package com.xzg.library.config.infrastructure.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzg.library.config.infrastructure.auth.token.JwtService;
import com.xzg.library.config.infrastructure.common.constant.SystemMessage;
import com.xzg.library.config.infrastructure.common.exception.UnauthorizedException;
import com.xzg.library.config.infrastructure.model.HeaderUserModel;
import com.xzg.library.config.infrastructure.utility.SplitHeaderToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.auth
 * @className: ApiHeaderAop
 * @author: xzg
 * @description: TODO
 * @date: 14/11/2023-下午 8:26
 * @version: 1.0
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class ApiHeaderAop {


    @Resource
    private ObjectMapper mapper;
    @Resource
    private JwtService jwtService;

    @Around(" @within(apiHeader)")
    public Object invokeWithIn(ProceedingJoinPoint point, ApiHeader apiHeader) throws Throwable {
        return invokeAround(point, apiHeader);
    }

    @Around("@annotation(apiHeader)")
    public Object invokeAround(ProceedingJoinPoint point, ApiHeader apiHeader) throws Throwable {
        // api header是否必须
        boolean required = apiHeader.required();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request;
        if (requestAttributes != null) {
            Object requestObj = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            if (requestObj instanceof HttpServletRequest) {
                request = (HttpServletRequest) requestObj;
//                final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                String jwtToken = SplitHeaderToken.splitAuthTokenStr(request);
                if (!StringUtils.hasText(jwtToken) && required) {
                    throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                } else {
                    if (StringUtils.hasText(jwtToken)) {
                        try {
                            String userName = jwtService.extractUsername(jwtToken);
                            HeaderUserModel userModel = HeaderUserModel.builder().userName(userName).build();
                            if (userModel == null) {
                                throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                            }
                            // 写header线程缓存
                            ApiHeaderUtil.setHeader(userModel);
                        } catch (Exception e) {
                            log.warn("header {}:{}", jwtToken,e.getMessage());
                            throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                        }
                    }
                }
            }
        }
        if (ApiHeaderUtil.getHeader() == null && required) {
            // 缺少用户信息, 抛出异常
            throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
        }
        try {
            return point.proceed();
        } finally {
            // 清楚header线程缓存
            ApiHeaderUtil.removeHeader();
        }
    }
}

    