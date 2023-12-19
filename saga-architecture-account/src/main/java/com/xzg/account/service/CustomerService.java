package com.xzg.account.service;


import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerDao;
import com.xzg.library.config.infrastructure.model.Money;

/**
 * @Date 2023/12/19
 * @author xiongzhenggang
 */
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
    return customerDao.findById(customerId);
  }

}
