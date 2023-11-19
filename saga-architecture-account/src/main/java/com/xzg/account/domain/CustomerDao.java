package com.xzg.account.domain;

public interface CustomerDao {
  Customer findById(long id);
  Customer save(Customer customer);
}
