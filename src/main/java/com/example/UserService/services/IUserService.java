package com.example.UserService.services;

import com.example.UserService.dtos.UserCreateDto;
import com.example.UserService.dtos.UserDeleteDto;
import com.example.UserService.dtos.UserDto;
import com.example.UserService.dtos.UserEditDto;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserDto> findAll(Pageable pageable);

    User findUserByUsername(String username);
    UserDto createUser(UserCreateDto userCreateDto);
    UserDto deleteUser(UserDeleteDto userDeleteDto);
    UserDto editUser(UserEditDto userEditDto);
    UserDto findUserByUsername2(String username);

}
