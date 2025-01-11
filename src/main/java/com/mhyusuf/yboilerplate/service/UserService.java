package com.mhyusuf.yboilerplate.service;

import com.mhyusuf.yboilerplate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserById(Long id);
    void deleteUserById(Long id);
}