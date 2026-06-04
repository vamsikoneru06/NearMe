package com.nearme.service;

import com.nearme.dto.AuthResponse;
import com.nearme.dto.LoginRequest;
import com.nearme.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
