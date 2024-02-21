package com.xzg.account.dao;

import com.xzg.account.domain.AccountUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
public interface CustomerRepository extends CrudRepository<AccountUser, Long> {
}
