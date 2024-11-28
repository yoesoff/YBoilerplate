package com.mhyusuf.yboilerplate.repository;

import com.mhyusuf.yboilerplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Anda bisa menambahkan query tambahan di sini jika diperlukan
}

