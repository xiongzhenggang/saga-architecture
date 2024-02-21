package com.xzg.account.dao;

import com.xzg.account.domain.AccountUser;
import com.xzg.account.domain.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Component
public class CustomerDaoImpl implements CustomerDao {
  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public AccountUser findById(long id) {
    return customerRepository
            .findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException(String.format("Customer with id=%s is not found", id)));
  }

  @Override
  public AccountUser save(AccountUser accountUser) {
    return customerRepository.save(accountUser);
  }
}
