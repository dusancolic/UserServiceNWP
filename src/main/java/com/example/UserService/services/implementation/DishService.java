package com.example.UserService.services.implementation;

import com.example.UserService.dtos.dish.DishCreateDto;
import com.example.UserService.dtos.dish.DishDeleteDto;
import com.example.UserService.dtos.dish.DishDto;
import com.example.UserService.dtos.dish.DishEditDto;
import com.example.UserService.mappers.DishMapper;
import com.example.UserService.models.Dish;
import com.example.UserService.repositories.DishRepository;
import com.example.UserService.services.IDishService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Data
public class DishService implements IDishService {

    private DishRepository dishRepository;
    private DishMapper dishMapper;


    @Override
    public Page<DishDto> findAll(Integer page, Integer size) {
        return dishRepository.findAll(PageRequest.of(page, size, Sort.by("id").ascending())).map(dishMapper :: dishToDishDto);
    }

    @Override
    public Page<DishDto> findAllExisting(Integer page, Integer size) {
        return dishRepository.findAllExisting(PageRequest.of(page, size, Sort.by("id").ascending())).map(dishMapper :: dishToDishDto);
    }

    @Override
    public DishDto findDishByName(String name) {
        return dishMapper.dishToDishDto(dishRepository.findByName(name));
    }

    @Override
    public DishDto createDish(DishCreateDto dishCreateDto) {
        Dish dish = dishMapper.dishCreateDtoToDish(dishCreateDto);
        Dish createdDish = dishRepository.save(dish);
        return dishMapper.dishToDishDto(createdDish);
    }

    @Override
    public DishDto deleteDish(DishDeleteDto dishDeleteDto) {
        Dish dish = dishRepository.findByName(dishDeleteDto.getName());
        dish = dishMapper.dishDeleteDtoToDish(dish, dishDeleteDto);
        dishRepository.save(dish);
        return dishMapper.dishToDishDto(dish);
    }

    @Override
    public DishDto editDish(DishEditDto dishEditDto) {
        Dish dish = dishRepository.findById(dishEditDto.getId()).orElseThrow(()-> new RuntimeException("Dish not found"));
        Dish otherDish = dishRepository.findByName(dishEditDto.getName());
        if(otherDish != null && !Objects.equals(otherDish.getId(), dish.getId()))
            throw new RuntimeException("Dish with name: " + dishEditDto.getName() + " already exists!");

        dish = dishMapper.dishEditDtoToDish(dish, dishEditDto);
        dishRepository.save(dish);
        return dishMapper.dishToDishDto(dish);

    }
}
