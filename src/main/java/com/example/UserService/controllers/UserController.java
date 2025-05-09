package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.user.UserCreateDto;
import com.example.UserService.dtos.user.UserDeleteDto;
import com.example.UserService.dtos.user.UserDto;
import com.example.UserService.dtos.user.UserEditDto;
import com.example.UserService.services.implementation.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size){
        System.out.println("page: " + page + " size: " + size);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canRead())
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.findAll(page, size));
    }
    @GetMapping("{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canRead())
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.findUserByUsername2(username));
    }


    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserCreateDto userCreateDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canCreate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(userService.createUser(userCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> editUser(@RequestBody UserEditDto userEditDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canUpdate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(userService.editUser(userEditDto), HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDeleteDto userDeleteDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canDelete())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(userService.deleteUser(userDeleteDto), HttpStatus.OK);
    }}
