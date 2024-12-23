package com.example.UserService.mappers;

import com.example.UserService.dtos.error.ErrorDto;
import com.example.UserService.models.Error;
import org.springframework.stereotype.Component;


@Component
public class ErrorMapper {

    public ErrorDto errorToErrorDto(Error error) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setOperation(error.getOperation());
        errorDto.setId(error.getId());
        errorDto.setMessage(error.getMessage());
        errorDto.setOrder(error.getForOrder());
        errorDto.setTime(error.getTime());
        errorDto.setUser(error.getUser().getUsername());

        return errorDto;
    }

}
