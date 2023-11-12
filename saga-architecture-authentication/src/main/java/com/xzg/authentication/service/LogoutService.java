package com.xzg.authentication.service;

import com.xzg.authentication.dao.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * 登录以及token的校验已经说过了,现在就差一个退出登录了.大家是否还记得我们之前配置过一个退出登录的请求路径:
 * /api/v1/auth/logout.当我们请求请求这个路径的时候,security会帮我们找到对应的LogoutHandler,
 * 然后调用logout方法实现退出登录.
 *
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    /**
     * security帮我们做了很多的事情,
     * 只需要把token置为失效状态,然后清除掉SecurityContextHolder上下文,就解决了全部的问题
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        //从请求头中获取鉴权信息
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        //接续出token
        jwt = authHeader.substring(7);
        //从数据库中查询出token信息
        var storedToken = tokenRepository.findByTokenStr(jwt)
                .orElse(null);
        if (storedToken != null) {
            //设置token过期
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            //清除SecurityContextHolder上下文
            SecurityContextHolder.clearContext();
        }
    }
}