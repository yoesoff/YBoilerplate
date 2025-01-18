package com.mhyusuf.yboilerplate.auth.controller;

import com.mhyusuf.yboilerplate.auth.dto.JwtAuthResponse;
import com.mhyusuf.yboilerplate.auth.dto.LoginDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    // Build Login REST API
    @GetMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = "Bearer itsadumytoken";

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}