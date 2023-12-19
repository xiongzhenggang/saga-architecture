package com.xzg.account.controller;

import com.xzg.account.domain.Customer;
import com.xzg.account.model.AccountDto;
import com.xzg.account.service.CustomerService;
import com.xzg.library.config.infrastructure.configuration.EnableResponseBodyWrap;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiongzhenggang
 */
@EnableResponseBodyWrap
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/{customerId}")
    public CommonResponse<Customer> getName(@PathVariable("customerId") Long customerId){
        return CommonResponse.success(customerService.getCustomerById(customerId));
    }
    @PostMapping("")
    public Customer createCustomer(@RequestBody AccountDto dto){
        return customerService.createCustomer(dto.getName() ,dto.getCreditLimit());
    }


}
