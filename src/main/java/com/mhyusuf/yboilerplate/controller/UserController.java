package com.mhyusuf.yboilerplate.controller;


import com.mhyusuf.yboilerplate.model.User;
import com.mhyusuf.yboilerplate.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /users
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/detail")
    public User getUserByParam(@RequestParam(required = false) Long id) {
        if (id != null) {
            return userService.getUser(id);
        }
        return null; // atau kembalikan daftar user kalau id null
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ? "User deleted" : "User not found";
    }

    // BONUS: GET /users
    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }
}

