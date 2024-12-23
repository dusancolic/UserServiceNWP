package com.example.UserService.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDto {

    @NotBlank(message = "First name is required!")
    private String firstname;
    @NotBlank(message = "Last name is required!")
    private String lastname;
    @Email(message = "Username should be valid!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;
    private boolean can_read;
    private boolean can_create;
    private boolean can_update;
    private boolean can_delete;
    private boolean can_search_order;
    private boolean can_place_order;
    private boolean can_cancel_order;
    private boolean can_track_order;
    private boolean can_schedule_order;
}
