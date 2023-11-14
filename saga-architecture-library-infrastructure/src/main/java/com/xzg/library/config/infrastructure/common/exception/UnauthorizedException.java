package com.xzg.library.config.infrastructure.common.exception;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.common.exception
 * @className: UnauthorizedException
 * @author: xzg
 * @description: TODO
 * @date: 14/11/2023-下午 8:29
 * @version: 1.0
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("error");
    }
    public UnauthorizedException(String message) {
        super(message);
    }

}


    