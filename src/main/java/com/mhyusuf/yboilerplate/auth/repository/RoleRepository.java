package com.mhyusuf.yboilerplate.auth.repository;

import com.mhyusuf.yboilerplate.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}