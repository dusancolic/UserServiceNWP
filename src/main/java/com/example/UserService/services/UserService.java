package com.example.UserService.services;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.UserCreateDto;
import com.example.UserService.dtos.UserDeleteDto;
import com.example.UserService.dtos.UserDto;
import com.example.UserService.dtos.UserEditDto;
import com.example.UserService.mappers.UserMapper;
import com.example.UserService.models.User;
import com.example.UserService.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Data
public class UserService implements IUserService, UserDetailsService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Pronalazak korisnika sa username-om: " + username);
        System.out.println(userRepository.findAll().size() + "  <- size");
        User user = findUserByUsername(username);

        if (user == null || user.isDeleted()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        System.out.println("Korisnik pronađen: " + user.getUsername());

        return new CustomUserDetails(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
       User user = userMapper.userCreateDtoToUser(userCreateDto);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
       return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto deleteUser(UserDeleteDto userDeleteDto) {
        User user = findUserByUsername(userDeleteDto.getUsername());
        user = userMapper.userDeleteDtoToUser(user, userDeleteDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto editUser(UserEditDto userEditDto) {

        User user = userRepository.findById(userEditDto.getId()).orElseThrow(()-> new RuntimeException("User not found"));
        User otherUser = findUserByUsername(userEditDto.getUsername());
        if(otherUser != null && !Objects.equals(otherUser.getId(), user.getId())){
            throw new RuntimeException("User with username " + userEditDto.getUsername() + " already exists");
        }
        user = userMapper.userEditDtoToUser(user, userEditDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto findUserByUsername2(String username) {
        return userMapper.userToUserDto(userRepository.findByUsername(username));
    }
}
