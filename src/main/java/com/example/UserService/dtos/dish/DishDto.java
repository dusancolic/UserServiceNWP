package com.example.UserService.dtos.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    private Long id;
    private String name;
    private Double price;
    private boolean deleted;
}
