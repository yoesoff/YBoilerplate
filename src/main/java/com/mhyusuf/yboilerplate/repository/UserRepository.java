package com.mhyusuf.yboilerplate.repository;

import com.mhyusuf.yboilerplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Anda bisa menambahkan query tambahan di sini jika diperlukan
}

