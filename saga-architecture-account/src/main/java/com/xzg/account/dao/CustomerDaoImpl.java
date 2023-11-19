package com.xzg.account.dao;

import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {
  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public Customer findById(long id) {
    return customerRepository
            .findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException(String.format("Customer with id=%s is not found", id)));
  }

  @Override
  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }
}
