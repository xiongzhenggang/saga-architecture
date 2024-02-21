package com.xzg.account.domain;

public interface CustomerDao {
  AccountUser findById(long id);
  AccountUser save(AccountUser accountUser);
}
