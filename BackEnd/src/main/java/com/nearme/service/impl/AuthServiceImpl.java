package com.nearme.service.impl;

import com.nearme.dto.AuthResponse;
import com.nearme.dto.LoginRequest;
import com.nearme.dto.RegisterRequest;
import com.nearme.dto.UserDTO;
import com.nearme.exception.AuthenticationException;
import com.nearme.exception.ValidationException;
import com.nearme.model.User;
import com.nearme.repository.UserRepository;
import com.nearme.security.JwtTokenProvider;
import com.nearme.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           UserDetailsService userDetailsService) {
        this.userRepository    = userRepository;
        this.passwordEncoder   = passwordEncoder;
        this.jwtTokenProvider  = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already in use: " + request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);
        return new AuthResponse(token, UserDTO.from(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);
        return new AuthResponse(token, UserDTO.from(user));
    }
}
