package com.mhyusuf.controller.rest;

import com.mhyusuf.auth.entity.User;
import com.mhyusuf.dto.UserDto;
import com.mhyusuf.dto.UserMapper;
import com.mhyusuf.exception.ResourceNotFoundException;
import com.mhyusuf.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        return UserMapper.toDto(userService.createUser(user));
    }

    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return UserMapper.toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return UserMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }
}
