package com.example.UserService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
   private Integer id;
   private String firstname;
   private String lastname;
   private String username;
   private boolean can_read;
   private boolean can_create;
   private boolean can_update;
   private boolean can_delete;
   private boolean deleted;
}
