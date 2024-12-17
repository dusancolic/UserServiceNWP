package com.example.UserService.services;

import com.example.UserService.dtos.*;
import com.example.UserService.models.Dish;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;

public interface IDishService {
    Page<DishDto> findAll(Integer page, Integer size);
    Page<DishDto> findAllExisting(Integer page, Integer size);
    DishDto findDishByName(String name);
    DishDto createDish(DishCreateDto dishCreateDto);
    DishDto deleteDish(DishDeleteDto dishDeleteDto);
    DishDto editDish(DishEditDto dishEditDto);

}
