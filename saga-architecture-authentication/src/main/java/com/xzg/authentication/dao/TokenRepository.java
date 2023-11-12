package com.xzg.authentication.dao;

import com.xzg.authentication.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository  extends JpaRepository<Token, String> {
    List<Token> findAllValidTokenByUserId (Integer userId);

    Optional<Token> findByTokenStr(String jwt);
}
