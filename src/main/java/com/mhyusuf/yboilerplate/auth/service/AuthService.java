package com.mhyusuf.yboilerplate.auth.service;

import com.mhyusuf.yboilerplate.auth.dto.LoginDto;
import com.mhyusuf.yboilerplate.auth.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}