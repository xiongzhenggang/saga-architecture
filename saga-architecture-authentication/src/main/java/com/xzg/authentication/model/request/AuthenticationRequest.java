package com.xzg.authentication.model.request;

import lombok.Data;

/**
 * @author xiongzhenggang
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
