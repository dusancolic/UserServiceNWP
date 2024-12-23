package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.dish.DishCreateDto;
import com.example.UserService.dtos.dish.DishDeleteDto;
import com.example.UserService.dtos.dish.DishDto;
import com.example.UserService.dtos.dish.DishEditDto;
import com.example.UserService.services.implementation.DishService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/dishes")
public class DishController {
    private DishService dishService;

    public DishController(DishService dishService){
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<Page<DishDto>> getAllDishes(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size){
        System.out.println("page: " + page + " size: " + size);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canRead())
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok(dishService.findAll(page, size));
    }

    @GetMapping("/menu")
    public ResponseEntity<Page<DishDto>> getAllExistingDishes(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size){
        System.out.println("page: " + page + " size: " + size);
        return ResponseEntity.ok(dishService.findAllExisting(page, size));
    }

    @GetMapping("{name}")
    public ResponseEntity<DishDto> getDishByName(@PathVariable String name){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canRead())
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok(dishService.findDishByName(name));
    }


    @PostMapping
    public ResponseEntity<DishDto> addDish(@RequestBody DishCreateDto dishCreateDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canCreate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(dishService.createDish(dishCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DishDto> editDish(@RequestBody DishEditDto dishEditDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canUpdate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(dishService.editDish(dishEditDto), HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<DishDto> deleteDish(@RequestBody DishDeleteDto dishDeleteDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canDelete())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(dishService.deleteDish(dishDeleteDto), HttpStatus.OK);
    }
}
