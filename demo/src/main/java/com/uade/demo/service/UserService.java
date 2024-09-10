package com.uade.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.demo.entity.User;
import com.uade.demo.entity.dto.UserDTO;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface UserService {
    public List<UserDTO> getUsers();

    public UserDTO getUserById(Long categoryId) throws ItemNotFoundException;

    public UserDTO updatePassword(Long userId, String newPassword) throws ItemNotFoundException;
}
