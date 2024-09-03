package com.uade.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.demo.entity.Role;
import com.uade.demo.entity.User;
import com.uade.demo.exceptions.UserDuplicateException;

public interface UserService {
    public Page<User> getUsers(PageRequest pageRequest);

    public Optional<User> getUserById(Long categoryId);

    public User createUser(String username, String password, String name, String surname, String mail, Role role)
        throws UserDuplicateException;
}
