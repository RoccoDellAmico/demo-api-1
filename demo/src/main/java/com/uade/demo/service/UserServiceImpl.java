package com.uade.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.demo.entity.Role;
import com.uade.demo.entity.User;
import com.uade.demo.exceptions.UserDuplicateException;
import com.uade.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public Optional<User> getUserById(Long categoryId) {
        return userRepository.findById(categoryId);
    }

    public User createUser(String username, String password, String name, String surname, String mail, Role role)
                throws UserDuplicateException {
        Optional<User> users = userRepository.findByEmail(mail);
        if (users.isEmpty())
            return userRepository.save(new User(username, password, name, surname, mail, role));
        throw new UserDuplicateException();
    }

    
}
