package com.example.UserService.dtos;

import com.example.UserService.models.Dish;
import com.example.UserService.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCreateDto {

    private List<Dish> dishes;
}
