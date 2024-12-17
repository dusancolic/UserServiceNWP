package com.example.UserService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishDeleteDto {

    private String name;
    private boolean deleted;
}
