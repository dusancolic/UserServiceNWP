package com.example.UserService.mappers;

import com.example.UserService.dtos.order.OrderCreateDto;
import com.example.UserService.dtos.order.OrderDto;
import com.example.UserService.dtos.order.OrderEditDto;
import com.example.UserService.models.*;
import org.springframework.stereotype.Component;

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

    public Order orderEditDtoToOrder(Order order, OrderEditDto orderEditDto) {
        order.setOrderStatus(OrderStatus.valueOf(orderEditDto.getOrderStatus()));
        if(order.getOrderStatus().equals(OrderStatus.CANCELED))
            order.setActive(false);
        return order;
    }
}
