package com.example.UserService.services;

import com.example.UserService.dtos.order.*;
import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    //Page<OrderDto> findAll(Integer page, Integer size);
    //Page<OrderDto> findAllByUsername(Integer page, Integer size, String username);
    Page<OrderDto> searchOrders(Integer page, Integer size, OrderSearchDto orderSearchDto);
    OrderDto createOrder(OrderCreateDto orderCreateDto, User user);
    OrderDto editOrder(OrderEditDto orderEditDto);
    OrderDto scheduleOrder(OrderScheduleDto orderScheduleDto, User user);
    OrderDto fromScheduledToOrdered(Order order);
    String getOrderStatus(Long id);

    // Metoda koja ažurira status porudžbine asinhrono
    void updateOrderStatus(Order order);
}
