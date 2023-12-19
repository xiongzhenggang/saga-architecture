package com.xzg.authentication.dao;

import com.xzg.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String userName, String password);
    /**
     *
     * @param email
     * @return
     */
     Optional<User> findByEmail(String email);

    /**
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);
}