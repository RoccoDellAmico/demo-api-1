package com.uade.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.User;
import com.uade.demo.entity.dto.UserDTO;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws ItemNotFoundException{
        UserDTO result = userService.getUserById(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/public/users/{userId}/password/update")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long userId,@RequestBody Map<String, String> request) 
        throws ItemNotFoundException{
        String newPassword = request.get("newPassword");
        UserDTO user = userService.updatePassword(userId, newPassword);
        return ResponseEntity.ok(user);
    }
}
