package com.xzg.authentication.api;

import com.xzg.authentication.service.AuthenticationService;
import com.xzg.authentication.model.request.AuthenticationRequest;
import com.xzg.authentication.model.request.RegisterRequest;
import com.xzg.authentication.model.rresponse.AuthenticationResponse;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 用户注册: 接收到用户传递过来的信息,在数据库中生成用户信息(密码会通过passwordEncoder进行加密).用户信息保存成功后,会根据用户信息创建一个鉴权token和一个refreshToken
 * 用户登录: 获取到用户传递的账号密码后,会创建一个UsernamePasswordAuthenticationToken对象.然后通过authenticationManager的authenticate方法进行校验,如果出现错误会根据错误的不同抛出不同的异常.在实际开发中可以通过捕获的异常类型不同来创建响应提示.
 * @author xiongzhenggang
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * 注册方法
     * @param request 请求体
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public CommonResponse<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return CommonResponse.success(service.register(request));
    }

    /**
     * 鉴权(登录方法)
     * @param request 请求体
     * @return ResponseEntity
     */
    @PostMapping("/login")
    public CommonResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return CommonResponse.success(service.authenticate(request));
    }

    /**
     * 刷新token
     * @param request 请求体
     * @param response 响应体
     * @throws IOException 异常
     */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
