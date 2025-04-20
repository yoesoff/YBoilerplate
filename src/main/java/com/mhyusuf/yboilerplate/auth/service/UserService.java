package com.mhyusuf.yboilerplate.auth.service;

import com.mhyusuf.yboilerplate.auth.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserById(UUID id);
    void deleteUserById(UUID id);
}