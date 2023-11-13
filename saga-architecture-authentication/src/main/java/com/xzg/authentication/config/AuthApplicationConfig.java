package com.xzg.authentication.config;

import com.xzg.authentication.dao.UserRepository;
import com.xzg.authentication.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 指定我们如何从数据库中根据用户账号获取用户信息
 * 指定用户密码的加密器passwordEncoder
 */
@Configuration
@RequiredArgsConstructor
public class AuthApplicationConfig {

    /**
     * 访问用户数据表
     */
    private final UserRepository repository;

    private  final PasswordEncoder passwordEncoder;
    /**
     * 获取用户详情Bean
     * 根据email查询是否存在用户,如果不存在throw用户未找到异常
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //调用repository的findByEmail方法,来获取用户信息,如果存在则返回,如果不存在则抛出异常
        return username -> repository.findByUserName(username)
                .map(e-> UserAccount.builder().user(e).build())
                //这里使用的Option的orElseThrow方法,如果存在则返回,如果不存在则抛出异常
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * 身份验证Bean
     * 传入获取用户信息的bean & 密码加密器
     * 可以回看一下SecurityConfiguration中 AuthenticationProvider的配置,使用的就是这里注入到容器中的Bean
     * 这个bean 主要是用于用户登录时的身份验证,当我们登录的时候security会帮我们调用这个bean的authenticate方法
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //设置获取用户信息的bean
        authProvider.setUserDetailsService(userDetailsService());
        //设置密码加密器
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * 身份验证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}