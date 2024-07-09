package com.xzg.authentication.api;

import com.xzg.authentication.dao.UserRepository;
import com.xzg.authentication.entity.User;
import com.xzg.library.config.infrastructure.auth.ApiHeader;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')") //用户需要ADMIN角色才能访问
public class AdminController {
    @Resource
    private UserRepository repository; //访问user数据库

    @GetMapping("/{userName}")
//    @PreAuthorize("hasAuthority('admin:read')") //用户需要admin:read权限才能访问
    @ApiHeader
    public User getUserByName(@PathVariable(value = "userName") String userName) {
        return repository.findByUsername(userName).orElse(new User());
    }
//    @PostMapping
//    @PreAuthorize("hasAuthority('admin:create')") //用户需要admin:create权限才能访问
//    public String post() {
//        return "POST:: admin controller";
//    }
//    @PutMapping
//    @PreAuthorize("hasAuthority('admin:update')")
//    public String put() {
//        return "PUT:: admin controller";
//    }
//    @DeleteMapping
//    @PreAuthorize("hasAuthority('admin:delete')")
//    public String delete() {
//        return "DELETE:: admin controller";
//    }
}