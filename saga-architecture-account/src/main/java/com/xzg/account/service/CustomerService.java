package com.xzg.account.service;


import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerDao;
import com.xzg.library.config.infrastructure.auth.ApiHeaderUtil;
import com.xzg.library.config.infrastructure.model.Money;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2023/12/19
 * @author xiongzhenggang
 */
@Slf4j
public class CustomerService {

  private CustomerDao customerDao;

  public CustomerService(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Customer createCustomer(String name, Money creditLimit) {
    Customer customer  = new Customer(name, creditLimit);
    return customerDao.save(customer);
  }

  /**
   *
   * @param customerId
   * @return
   */
  public Customer getCustomerById( Long customerId){
    log.info("操作人：{}", ApiHeaderUtil.getHeader());
    return customerDao.findById(customerId);
  }

}
