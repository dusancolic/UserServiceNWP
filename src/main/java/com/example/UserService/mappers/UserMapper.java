package com.example.UserService.mappers;

import com.example.UserService.dtos.user.UserCreateDto;
import com.example.UserService.dtos.user.UserDeleteDto;
import com.example.UserService.dtos.user.UserDto;
import com.example.UserService.dtos.user.UserEditDto;
import com.example.UserService.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userToUserDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setDeleted(user.isDeleted());
        userDto.setCan_read(user.isCan_read());
        userDto.setCan_delete(user.isCan_delete());
        userDto.setCan_update(user.isCan_update());
        userDto.setCan_create(user.isCan_create());
        userDto.setCan_search_order(user.isCan_search_order());
        userDto.setCan_place_order(user.isCan_place_order());
        userDto.setCan_cancel_order(user.isCan_cancel_order());
        userDto.setCan_track_order(user.isCan_track_order());
        userDto.setCan_schedule_order(user.isCan_schedule_order());
        System.out.println("Mapper 1: " + user.isAdmin());
        userDto.setAdmin(user.isAdmin());
        System.out.println("Mapper 2: " + userDto.isAdmin());

        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto)
    {
        User user = new User();
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setCan_read(userCreateDto.isCan_read());
        user.setCan_create(userCreateDto.isCan_create());
        user.setCan_update(userCreateDto.isCan_update());
        user.setCan_delete(userCreateDto.isCan_delete());
        user.setCan_cancel_order(userCreateDto.isCan_cancel_order());
        user.setCan_place_order(userCreateDto.isCan_place_order());
        user.setCan_search_order(userCreateDto.isCan_search_order());
        user.setCan_schedule_order(userCreateDto.isCan_schedule_order());
        user.setCan_track_order(userCreateDto.isCan_track_order());
        user.setAdmin(false);
        user.setDeleted(false);
        return user;
    }

    public User userDeleteDtoToUser(User user, UserDeleteDto userDeleteDto) {
        user.setDeleted(userDeleteDto.isDeleted());
        return user;
    }

    public User userEditDtoToUser(User user, UserEditDto userEditDto) {
        user.setFirstname(userEditDto.getFirstname());
        user.setLastname(userEditDto.getLastname());
        user.setUsername(userEditDto.getUsername());
        user.setCan_read(userEditDto.isCan_read());
        user.setCan_create(userEditDto.isCan_create());
        user.setCan_update(userEditDto.isCan_update());
        user.setCan_delete(userEditDto.isCan_delete());
        user.setCan_cancel_order(userEditDto.isCan_cancel_order());
        user.setCan_place_order(userEditDto.isCan_place_order());
        user.setCan_search_order(userEditDto.isCan_search_order());
        user.setCan_track_order(userEditDto.isCan_track_order());
        user.setCan_schedule_order(userEditDto.isCan_schedule_order());
        return user;
    }
}
