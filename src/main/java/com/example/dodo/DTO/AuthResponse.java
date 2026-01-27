package com.example.dodo.DTO;

public class AuthResponse {
    private String token;
    private String message;
    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
