package com.xzg.authentication.config.filter;

import com.xzg.authentication.dao.TokenRepository;
import com.xzg.library.config.infrastructure.auth.token.JwtService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 请求过滤主要是在每次请求的时候动态解析token来获取用户信息以及权限,来保证请求资源的安全性.防止越权访问等.
 * 从请求头中获取到token.验证token的有效性并解析token中的信息存储到SecurityContextHolder上下文中,方便后续的使用
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private  JwtService jwtService;
    @Resource
    private  UserDetailsService userDetailsService;
    @Resource
    private  TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //判断请求是否为登录请求，如果是登录请求则不进行处理
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        //从请求头中获取鉴权authHeader
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        //如果不存在Token或者Token不已Bearer开头，则不进行处理
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //从authHeader中截取出Token信息
        jwt = authHeader.substring(7);
        //从Token中获取userEmail(账户)
        userName = jwtService.extractUsername(jwt);
        //SecurityContextHolder 中的 Authentication 为空时，才进行处理
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //获取用户信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            //从数据库中查询Token并判断Token状态是否正常
            var isTokenValid = tokenRepository.findByTokenStr(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            //如果Token有效,并且Token状态正常,将用户信息存储到SecurityContextHolder
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, //用户信息
                        null,
                        userDetails.getAuthorities() //用户的权限
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request) //访问信息
                );
                //将用户信息以及权限保存到 SecurityContextHolder的上下文中,方便后续使用
                //eg: 获取当前用户id,获取当前用户权限等等
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}