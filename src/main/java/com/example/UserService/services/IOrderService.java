package com.example.UserService.services;

import com.example.UserService.dtos.*;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    //Page<OrderDto> findAll(Integer page, Integer size);
    //Page<OrderDto> findAllByUsername(Integer page, Integer size, String username);
    Page<OrderDto> searchOrders(Integer page, Integer size, LocalDateTime from, LocalDateTime to, Long userId, List<OrderStatus> statuses);
    OrderDto createOrder(OrderCreateDto orderCreateDto, User user);
    OrderDto editOrder(OrderEditDto orderEditDto);
}
