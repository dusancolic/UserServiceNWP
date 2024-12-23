package com.example.UserService.services.implementation;

import com.example.UserService.dtos.ErrorCreateDto;
import com.example.UserService.dtos.ErrorDto;
import com.example.UserService.mappers.ErrorMapper;
import com.example.UserService.models.Dish;
import com.example.UserService.models.Error;
import com.example.UserService.models.User;
import com.example.UserService.repositories.ErrorRepository;
import com.example.UserService.services.IErrorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Data
public class ErrorService implements IErrorService {

    private ErrorMapper errorMapper;
    private ErrorRepository errorRepository;

    @Override
    public Page<ErrorDto> findAll(Integer page, Integer size) {
        return errorRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending())).map(errorMapper :: errorToErrorDto);
    }

    @Override
    public Page<ErrorDto> findAllByUsername(Integer page, Integer size, String username) {
        return errorRepository.findAllByUsername(PageRequest.of(page, size, Sort.by("id").descending()),username).map(errorMapper :: errorToErrorDto);
    }

    @Override
    public ErrorDto createError(ErrorCreateDto errorCreateDto, User user) {
        Error error = errorMapper.errorCreateDtoToError(errorCreateDto);
        Error createdError = errorRepository.save(error);
        return errorMapper.errorToErrorDto(createdError);
    }
}
