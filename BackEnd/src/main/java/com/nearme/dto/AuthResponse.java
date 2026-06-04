package com.nearme.dto;

public class AuthResponse {

    private final String token;
    private final String tokenType = "Bearer";
    private final UserDTO user;

    public AuthResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken()     { return token; }
    public String getTokenType() { return tokenType; }
    public UserDTO getUser()     { return user; }
}
