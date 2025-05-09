package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.error.ErrorDto;
import com.example.UserService.services.implementation.ErrorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/errors")
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

}
