package com.mhyusuf.yboilerplate.auth.runner;

import com.mhyusuf.yboilerplate.auth.entity.Role;
import com.mhyusuf.yboilerplate.auth.entity.User;
import com.mhyusuf.yboilerplate.auth.repository.RoleRepository;
import com.mhyusuf.yboilerplate.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");

        createUserIfNotExists("yoesoff@gmail.com", "yoesoff", "yoesoff", "ROLE_USER");
        createUserIfNotExists("admin@gmail.com", "admin", "admin", "ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createUserIfNotExists(String email, String username, String rawPassword, String roleName) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));

            // Ambil Role dari database dalam konteks yang dikelola
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
            user.setRoles(Collections.singleton(role));

            userRepository.save(user);
        }
    }

}
