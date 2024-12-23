package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.ErrorCreateDto;
import com.example.UserService.dtos.ErrorDto;
import com.example.UserService.dtos.OrderCreateDto;
import com.example.UserService.dtos.OrderDto;
import com.example.UserService.services.implementation.ErrorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/error")
public class ErrorController {

    private ErrorService errorService;

    public ErrorController(ErrorService errorService){
        this.errorService = errorService;
    }

    @GetMapping
    public ResponseEntity<Page<ErrorDto>> getAllErrors(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size){
        System.out.println("page: " + page + " size: " + size);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canSearchOrder())
            return ResponseEntity.status(403).build();

        if(customUserDetails.isAdmin())
            return ResponseEntity.ok(errorService.findAll(page, size));
        return ResponseEntity.ok(errorService.findAllByUsername(page, size, customUserDetails.getUsername()));
    }



    @PostMapping
    public ResponseEntity<ErrorDto> addError(@RequestBody ErrorCreateDto errorCreateDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canCreate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(errorService.createError(errorCreateDto, customUserDetails.getUser()), HttpStatus.CREATED);
    }
}
