package com.uade.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.uade.demo.entity.User;
import com.uade.demo.entity.dto.UserDTO;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : users){
            UserDTO userDTO = mapToUserDTO(user);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public UserDTO getUserById(Long categoryId) throws ItemNotFoundException {
        User user = userRepository.findById(categoryId).orElseThrow(()-> new ItemNotFoundException());
        UserDTO userDTO = mapToUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO updatePassword(Long userId, String newPassword) throws ItemNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new ItemNotFoundException());
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return mapToUserDTO(user);
    }
}
