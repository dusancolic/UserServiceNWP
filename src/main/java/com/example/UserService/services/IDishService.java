package com.example.UserService.services;

import com.example.UserService.dtos.dish.DishCreateDto;
import com.example.UserService.dtos.dish.DishDeleteDto;
import com.example.UserService.dtos.dish.DishDto;
import com.example.UserService.dtos.dish.DishEditDto;
import org.springframework.data.domain.Page;

public interface IDishService {
    Page<DishDto> findAll(Integer page, Integer size);
    Page<DishDto> findAllExisting(Integer page, Integer size);
    DishDto findDishByName(String name);
    DishDto createDish(DishCreateDto dishCreateDto);
    DishDto deleteDish(DishDeleteDto dishDeleteDto);
    DishDto editDish(DishEditDto dishEditDto);

}
