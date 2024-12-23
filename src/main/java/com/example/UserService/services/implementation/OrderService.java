package com.example.UserService.services.implementation;

import com.example.UserService.dtos.order.OrderCreateDto;
import com.example.UserService.dtos.order.OrderDto;
import com.example.UserService.dtos.order.OrderEditDto;
import com.example.UserService.mappers.DishMapper;
import com.example.UserService.mappers.OrderMapper;
import com.example.UserService.models.*;
import com.example.UserService.models.Error;
import com.example.UserService.repositories.DishRepository;
import com.example.UserService.repositories.ErrorRepository;
import com.example.UserService.repositories.OrderItemRepository;
import com.example.UserService.repositories.OrderRepository;
import com.example.UserService.services.IOrderService;
import com.example.UserService.specification.OrderSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Data
public class OrderService implements IOrderService {

    private final DishRepository dishRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ErrorRepository errorRepository;
    private OrderMapper orderMapper;
    private DishMapper dishMapper;


    @Override
    public Page<OrderDto> searchOrders(Integer page, Integer size, LocalDateTime from, LocalDateTime to, Long userId, List<OrderStatus> statuses) {
        Specification<Order> spec = Specification.where(from != null? OrderSpecification.withDateFrom(from) : null)
                .and(to != null? OrderSpecification.withDateTo(to) : null)
                .and(userId != null? OrderSpecification.byUserId(userId) : null)
                .and(!Objects.isNull(statuses) && !statuses.isEmpty()? OrderSpecification.withStatuses(statuses) : null);

        return orderRepository.findAll(spec, PageRequest.of(page, size, Sort.by("id").descending())).map(orderMapper :: orderToOrderDto);
    }

    @Override
    public OrderDto createOrder(OrderCreateDto orderCreateDto, User user, boolean scheduled) {

        List<OrderStatus> statuses = new ArrayList<>(List.of(OrderStatus.PREPARING, OrderStatus.IN_DELIVERY));
        if(orderRepository.numberOfOrdersInProgress(statuses) == 3)
        {
            Error error = new Error();
            String dishes = orderCreateDto.getDishes().toString().substring(1, orderCreateDto.getDishes().toString().length() - 1);
            error.setForOrder(dishes);
            error.setTime(LocalDateTime.now());
            error.setUser(user);
            error.setMessage("Failed to create order, there are already 3 orders in progress");
            if (scheduled)
                error.setOperation("schedule");
            else
                error.setOperation("create");

            errorRepository.save(error);
            return null;
        }

        Order order = new Order();
        order.setOrderedBy(user);
        Order createdOrder = orderRepository.save(order);
        for(Dish dish : orderCreateDto.getDishes())
        {
            OrderItem orderItem = new OrderItem(order, dish);
            order.getItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }
        return orderMapper.orderToOrderDto(createdOrder);
    }

    @Override
    public OrderDto editOrder(OrderEditDto orderEditDto) {
        Order order = orderRepository.findById(orderEditDto.getId()).orElseThrow(()-> new RuntimeException("Order not found"));

        if(!order.getOrderStatus().equals(OrderStatus.ORDERED) && orderEditDto.getOrderStatus().equals(OrderStatus.CANCELED.toString()))
            return null;
        order = orderMapper.orderEditDtoToOrder(order, orderEditDto);
        orderRepository.save(order);
        return orderMapper.orderToOrderDto(order);
    }

}
