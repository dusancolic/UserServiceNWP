package com.example.UserService.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
