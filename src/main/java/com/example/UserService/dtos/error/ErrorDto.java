package com.example.UserService.dtos.error;

import com.example.UserService.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private Long id;
    private String order;
    private String user;
    private String message;
    private LocalDateTime time;
    private String operation;
}
