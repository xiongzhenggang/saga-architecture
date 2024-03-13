package com.xzg.account.service;


import com.xzg.account.domain.AccountUser;
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

  public AccountUser createCustomer(String name, Long userId,Money creditLimit) {
    AccountUser accountUser = new AccountUser(name,userId, creditLimit);
    return customerDao.save(accountUser);
  }

  /**
   * 修改
   * @param accountId
   * @param creditLimit
   * @return
   */
  public AccountUser update(Long accountId,Money creditLimit) {
    AccountUser accountUser = customerDao.findById(accountId);
    accountUser.setCreditLimit(creditLimit);
    return customerDao.save(accountUser);
  }

  /**
   *
   * @param customerId
   * @return
   */
  public AccountUser getCustomerById(Long customerId){
    log.info("操作人：{}", ApiHeaderUtil.getHeader());
    return customerDao.findById(customerId);
  }

}
