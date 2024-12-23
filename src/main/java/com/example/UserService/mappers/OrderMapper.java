package com.example.UserService.mappers;

import com.example.UserService.dtos.*;
import com.example.UserService.models.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDto orderToOrderDto(Order order) {

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setActive(order.isActive());
        orderDto.setOrderedBy(order.getOrderedBy());
        orderDto.setOrderStatus(String.valueOf(order.getOrderStatus()));
        orderDto.setOrderedAt(order.getOrderedAt());
        System.out.println("order mapper: " + orderDto);
        return orderDto;
    }

    public Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto, User user)
    {
        Order order = new Order();
        order.setOrderedBy(user);

        return order;
    }

    public Order orderEditDtoToOrder(Order order, OrderEditDto orderEditDto) {
        order.setOrderStatus(OrderStatus.valueOf(orderEditDto.getOrderStatus()));
        if(order.getOrderStatus().equals(OrderStatus.CANCELED))
            order.setActive(false);
        return order;
    }
}
