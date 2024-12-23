package com.example.UserService.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private boolean can_read;
    private boolean can_create;
    private boolean can_update;
    private boolean can_delete;
    private boolean can_search_order;
    private boolean can_place_order;
    private boolean can_cancel_order;
    private boolean can_track_order;
    private boolean can_schedule_order;
    private boolean admin;


    public LoginResponse(String jwt, boolean can_create, boolean can_read, boolean can_update,
                         boolean can_delete, boolean admin, boolean can_search_order, boolean can_place_order,
                         boolean can_cancel_order, boolean can_track_order, boolean can_schedule_order) {
        this.jwt = jwt;
        this.can_create = can_create;
        this.can_read = can_read;
        this.can_update = can_update;
        this.can_delete = can_delete;
        this.can_search_order = can_search_order;
        this.can_place_order = can_place_order;
        this.can_cancel_order = can_cancel_order;
        this.can_track_order = can_track_order;
        this.can_schedule_order = can_schedule_order;
        this.admin = admin;
    }
}
