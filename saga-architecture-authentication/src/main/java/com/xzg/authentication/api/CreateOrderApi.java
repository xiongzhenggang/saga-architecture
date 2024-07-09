package com.xzg.authentication.api;

import com.xzg.authentication.dao.UserRepository;
import com.xzg.authentication.entity.User;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.authentication.api
 * @className: CreateOrderApi
 * @author: xzg
 * @description: TODO
 * @date: 9/7/2024-下午 8:59
 * @version: 1.0
 */
@RestController
public class CreateOrderApi {
    @Resource
    private UserRepository repository; //访问user数据库
    @RequestMapping(value = "/api/v1/auth/orders", method = RequestMethod.POST)
    public CommonResponse<String> createOrder()  {
        User user = new User();
        user.setUsername("xuzhigang");
        user.setPassword("123456");
        user.setEmail("123456789@qq.com");
        user.setTelephone("15478781231");
        repository.save(user);
        return CommonResponse.success("create order success");
    }
}


    