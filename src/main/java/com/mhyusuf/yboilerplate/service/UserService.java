package com.mhyusuf.yboilerplate.service;

import com.mhyusuf.yboilerplate.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<Long, User> userStore = new HashMap<>();
    private long counter = 1;

    public User createUser(User user) {
        user.setId(counter++);
        userStore.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        userStore.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return userStore.get(id);
    }

    public boolean deleteUser(Long id) {
        return userStore.remove(id) != null;
    }

    public Collection<User> getAllUsers() {
        return userStore.values();
    }
}
