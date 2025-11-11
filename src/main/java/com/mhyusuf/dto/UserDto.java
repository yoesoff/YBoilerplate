package com.mhyusuf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    // Getters and setters
    private UUID id;
    private String email;
    private String username;
    private Set<String> roles;

    public void setId(UUID id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setRoles(Set<String> roles) { this.roles = roles; }
}
