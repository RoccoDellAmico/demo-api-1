package com.uade.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.token.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{

    @Query
    (value = "select t from Token t inner join user u on t.user.id = u.id where u.id = ?1 and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);

}
