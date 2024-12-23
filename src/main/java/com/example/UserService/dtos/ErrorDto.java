package com.example.UserService.dtos;

import com.example.UserService.models.Order;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private Long id;
    private Order order;
    private String message;
    private LocalDateTime time;
    private String operation;
}
