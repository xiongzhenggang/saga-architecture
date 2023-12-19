package com.xzg.account.controller;

import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerDao;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class TestController {

    @Resource
    private CustomerDao customerDao;
    @GetMapping("/{customerId}")
    public Customer getName(@PathVariable("customerId") Long customerId){
        return customerDao.findById(customerId);
    }
}
