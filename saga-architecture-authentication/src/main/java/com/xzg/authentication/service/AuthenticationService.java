package com.xzg.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzg.authentication.dao.TokenRepository;
import com.xzg.authentication.dao.UserRepository;
import com.xzg.authentication.entity.Token;
import com.xzg.authentication.entity.User;
import com.xzg.authentication.entity.UserAccount;
import com.xzg.authentication.entity.enums.TokenType;
import com.xzg.authentication.model.request.AuthenticationRequest;
import com.xzg.authentication.model.request.RegisterRequest;
import com.xzg.authentication.model.rresponse.AuthenticationResponse;
import com.xzg.authentication.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository; //访问user数据库
    private final TokenRepository tokenRepository; //访问token数据库
    private final PasswordEncoder passwordEncoder; //密码加密器
    private final JwtService jwtService; //JWT 相关方法
    private final AuthenticationManager authenticationManager; //Spring Security 认证管理器

    /**
     * 注册方法
     * @param request 请求体
     * @return AuthenticationResponse(自己封装的响应结构)
     */
    public AuthenticationResponse register(RegisterRequest request) {
        //构建用户信息
        var userEntity = new User();
        userEntity.setUserName(request.getUserName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRole(request.getRole());
        //将用户信息保存到数据库
        var savedUser = repository.save(userEntity);
        UserAccount userAccount = new UserAccount(savedUser);;
        //通过JWT方法生成Token
        var jwtToken = jwtService.generateToken(userAccount);
        //生成RefreshToken(刷新Token使用)
        var refreshToken = jwtService.generateRefreshToken(userAccount);
        //将Token保存到数据库
        saveUserToken(savedUser.getId(), jwtToken);
        //返回响应体
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 鉴权(登录)方法
     * @param request 请求体
     * @return AuthenticationResponse(自己封装的响应结构)
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //通过Spring Security 认证管理器进行认证
        //如果认证失败会抛出异常 eg:BadCredentialsException 密码错误 UsernameNotFoundException 用户不存在
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //通过邮箱查询用户信息,当前项目email就是账号
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        UserAccount userAccount = UserAccount.builder().user(user).build();
        //通过JWT方法生成Token
        var jwtToken = jwtService.generateToken(userAccount);
        //生成RefreshToken(刷新Token使用)
        var refreshToken = jwtService.generateRefreshToken(userAccount);
        //将之前所有的Token变成失效状态
        revokeAllUserTokens(user.getId());
        //保存新的Token到数据库
        saveUserToken(user.getId(), jwtToken);
        //封装响应体
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 保存用户Token方法
     * 构建Token实体后保存到数据库
     * @param userId 用户信息
     * @param jwtToken Token
     */
    private void saveUserToken(Integer userId, String jwtToken) {
        var token = Token.builder()
                .userId(userId)
                .tokenStr(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * 将用户所有Token变成失效状态
     * @param userId 用户信息
     */
    private void revokeAllUserTokens(Integer userId) {
        //获取用户所有有效的token
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(userId);
        if (validUserTokens.isEmpty()){
            return;
        }
        //如果存在还为失效的token,将token置为失效
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * 刷新token方法
     * @param request 请求体
     * @param response 响应体
     * @throws IOException 抛出IO异常
     */
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        //从请求头中获取中获取鉴权信息 AUTHORIZATION
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        //如果鉴权信息为空或者不是以Bearer 开头的,直接返回
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        //从鉴权信息中获取RefreshToken
        refreshToken = authHeader.substring(7);
        //从RefreshToken中获取用户信息
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            //根据用户信息查询用户,如果用户不存在抛出异常
            var user = this.repository.findByUserName(userName)
                    .orElseThrow();

            //验证Token是否有效
            var userAccount = new UserAccount(user);
            if (jwtService.isTokenValid(refreshToken, userAccount)) {
                //生成新的Token
                var accessToken = jwtService.generateToken(userAccount);
                revokeAllUserTokens(user.getId());
                saveUserToken(user.getId(), accessToken);
                //生成新的Token和RefreshToken并通过响应体返回
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}