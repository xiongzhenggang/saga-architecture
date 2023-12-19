package com.xzg.authentication.model.request;

import com.xzg.authentication.entity.Role;
import lombok.Data;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
