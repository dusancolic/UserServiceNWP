package com.example.UserService.mappers;

import com.example.UserService.dtos.ErrorCreateDto;
import com.example.UserService.dtos.ErrorDto;
import com.example.UserService.models.Error;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ErrorMapper {

    public ErrorDto errorToErrorDto(Error error) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setOperation(error.getOperation());
        errorDto.setId(error.getId());
        errorDto.setMessage(error.getMessage());
        errorDto.setOrder(error.getOrder());
        errorDto.setTime(error.getTime());

        return errorDto;
    }

    public Error errorCreateDtoToError(ErrorCreateDto errorCreateDto)
    {
        Error error = new Error();
        error.setTime(LocalDateTime.now());
        error.setMessage(errorCreateDto.getMessage());
        error.setOperation(errorCreateDto.getOperation());
        error.setOrder(errorCreateDto.getOrder());

        return error;
    }
}
