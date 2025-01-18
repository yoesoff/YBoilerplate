package com.mhyusuf.yboilerplate.auth.controller;

import com.mhyusuf.yboilerplate.auth.dto.JwtAuthResponse;
import com.mhyusuf.yboilerplate.auth.dto.LoginDto;
import com.mhyusuf.yboilerplate.auth.dto.RegisterDto;
import com.mhyusuf.yboilerplate.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String message = authService.register(registerDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}