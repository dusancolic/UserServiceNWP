package com.example.UserService.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private boolean can_read;
    private boolean can_create;
    private boolean can_update;
    private boolean can_delete;


    public LoginResponse(String jwt, boolean can_create, boolean can_read, boolean can_update, boolean can_delete) {
        this.jwt = jwt;
        this.can_create = can_create;
        this.can_read = can_read;
        this.can_update = can_update;
        this.can_delete = can_delete;
    }
}
