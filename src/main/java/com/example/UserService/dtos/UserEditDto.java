package com.example.UserService.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    private Integer id;
    @NotBlank(message = "First name is required!")
    private String firstname;
    @NotBlank(message = "Last name is required!")
    private String lastname;
    @Email(message = "Username should be valid!")
    private String username;
    private boolean can_read;
    private boolean can_create;
    private boolean can_update;
    private boolean can_delete;
}
