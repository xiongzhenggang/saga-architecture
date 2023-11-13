package com.xzg.authentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.authention
 * @className: App
 * @author: xzg
 * @description: 主类
 * @date: 9/11/2023-下午 8:41
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.xzg"})
public class AuthenticationApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApp.class, args);
    }
}


    