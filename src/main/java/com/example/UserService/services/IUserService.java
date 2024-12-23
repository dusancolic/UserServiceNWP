package com.example.UserService.services;

import com.example.UserService.dtos.user.UserCreateDto;
import com.example.UserService.dtos.user.UserDeleteDto;
import com.example.UserService.dtos.user.UserDto;
import com.example.UserService.dtos.user.UserEditDto;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;

public interface IUserService {
    Page<UserDto> findAll(Integer page, Integer size);

    User findUserByUsername(String username);
    UserDto createUser(UserCreateDto userCreateDto);
    UserDto deleteUser(UserDeleteDto userDeleteDto);
    UserDto editUser(UserEditDto userEditDto);
    UserDto findUserByUsername2(String username);

}
