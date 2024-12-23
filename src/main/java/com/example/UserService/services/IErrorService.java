package com.example.UserService.services;

import com.example.UserService.dtos.error.ErrorDto;
import org.springframework.data.domain.Page;

public interface IErrorService {

    Page<ErrorDto> findAll(Integer page, Integer size);
    Page<ErrorDto> findAllByUsername(Integer page, Integer size, String username);

    //ErrorDto createError(ErrorCreateDto errorCreateDto);
}
