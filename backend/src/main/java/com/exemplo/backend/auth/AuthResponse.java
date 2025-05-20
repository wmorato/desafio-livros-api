package com.exemplo.backend.auth;
import com.exemplo.backend.dto.UserDTO;


public class AuthResponse {
    private String token;
    private UserDTO user; // Adicione esse campo

    // getters e setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
}

