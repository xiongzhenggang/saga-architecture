package com.xzg.account.controller;

import com.xzg.account.domain.AccountUser;
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

//    @ApiHeader
    @GetMapping("/{customerId}")
    public CommonResponse<AccountUser> getById(@PathVariable("customerId") Long customerId){
        return CommonResponse.success(customerService.getCustomerById(customerId));
    }
    @PostMapping("")
    public AccountUser createAccount(@RequestBody AccountDto dto){
        return customerService.createCustomer(dto.getUserName(),dto.getUserId() ,dto.getCreditLimit());
    }

    @PutMapping("")
    public AccountUser updateAccount(@RequestBody AccountDto dto){
        return customerService.update(dto.getAccountId(),dto.getCreditLimit());
    }

}
