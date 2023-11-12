package com.xzg.authentication.config;

import com.xzg.authentication.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *代码主要实现了四块功能分别是:
 *
 * 放行不需要鉴权的路径(注册&登录,swagger)
 * 配置访问特定的接口用户需要的权限.(比如想要删除用户必须要有删除用户的权限)
 * 添加前置过滤器,用来从Token中判断用户是否合法和获取用户权限: jwtAuthFilter
 * 配置退出登录的Handler,以及监听的路径.当访问这个路径的时候会自动调用logoutHandler中的方法
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //关闭csrf(跨域)
                .csrf(AbstractHttpConfigurer::disable)
                //配置需要放行的路径
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**").permitAll()
//                        权限校验(需要登录的用户有指定的权限才可以)
                        .anyRequest().authenticated())
                //使用无状态Session
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                //添加jwt过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //设置logout(当调用这个接口的时候, 会调用logoutHandler的logout方法)
                .logout(logout->logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response,authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }
}