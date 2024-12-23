package com.example.UserService.dtos;

import com.example.UserService.models.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorCreateDto {
    @NotBlank(message = "Order is required!")
    private Order order;
    @NotBlank(message = "Message is required!")
    private String message;
    @NotBlank(message = "Operation is required!")
    private String operation;
}
