package com.uade.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "select u from User u where u.username = ?1")
    List<User> findByUsername(String username);
}
