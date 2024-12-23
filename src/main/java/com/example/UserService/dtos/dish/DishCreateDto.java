package com.example.UserService.dtos.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishCreateDto {
    private String name;
    private Double price;
}
