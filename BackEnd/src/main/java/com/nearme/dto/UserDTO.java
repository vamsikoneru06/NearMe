package com.nearme.dto;

import com.nearme.model.User;

import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private LocalDateTime createdAt;

    public static UserDTO from(User user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.createdAt = user.getCreatedAt();
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public User.Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
