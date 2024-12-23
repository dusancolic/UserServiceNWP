package com.example.UserService.mappers;

import com.example.UserService.dtos.dish.DishCreateDto;
import com.example.UserService.dtos.dish.DishDeleteDto;
import com.example.UserService.dtos.dish.DishDto;
import com.example.UserService.dtos.dish.DishEditDto;
import com.example.UserService.models.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public DishDto dishToDishDto(Dish dish) {

        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        dishDto.setDeleted(dish.isDeleted());

        return dishDto;
    }

    public Dish dishCreateDtoToDish(DishCreateDto dishCreateDto)
    {
        Dish dish = new Dish();
        dish.setPrice(dishCreateDto.getPrice());
        dish.setName(dishCreateDto.getName());
        dish.setDeleted(false);
        return dish;
    }

    public Dish dishDeleteDtoToDish(Dish dish, DishDeleteDto dishDeleteDto) {
        dish.setDeleted(dishDeleteDto.isDeleted());
        return dish;
    }

    public Dish dishEditDtoToDish(Dish dish, DishEditDto dishEditDto) {
        dish.setName(dishEditDto.getName());
        dish.setPrice(dishEditDto.getPrice());
        return dish;
    }
}
