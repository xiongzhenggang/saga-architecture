package com.xzg.authentication.model.request;

import com.xzg.authentication.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String userName; //名字
    private String email; //邮箱
    private String password; //密码
    private Role role;
}
