package com.example.UserService.services.implementation;

import com.example.UserService.dtos.order.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Page<OrderDto> searchOrders(Integer page, Integer size, OrderSearchDto orderSearchDto) {
        Specification<Order> spec = Specification.where(orderSearchDto.getFrom() != null ? OrderSpecification.withDateFrom(orderSearchDto.getFrom()) : null)
                .and(orderSearchDto.getTo() != null ? OrderSpecification.withDateTo(orderSearchDto.getTo()) : null)
                .and(orderSearchDto.getUsername() != null ? OrderSpecification.byUsername(orderSearchDto.getUsername()) : null)
                .and(!Objects.isNull(orderSearchDto.getStatuses()) && !orderSearchDto.getStatuses().isEmpty() ? OrderSpecification.withStatuses(orderSearchDto.getStatuses()) : null);
        return orderRepository.findAll(spec, PageRequest.of(page, size, Sort.by("id").descending())).map(orderMapper::orderToOrderDto);
    }

    @Override
    public OrderDto createOrder(OrderCreateDto orderCreateDto, User user) {
        List<OrderStatus> statuses = new ArrayList<>(List.of(OrderStatus.PREPARING, OrderStatus.IN_DELIVERY));
        if (orderRepository.numberOfOrdersInProgress(statuses) == 3) {
            Error error = new Error();
            String dishes = orderCreateDto.getDishes().toString().substring(1, orderCreateDto.getDishes().toString().length() - 1);
            error.setForOrder(dishes);
            error.setTime(LocalDateTime.now());
            error.setUser(user);
            error.setMessage("Failed to create order, there are already 3 orders in progress");
            error.setOperation("create");

            errorRepository.save(error);
            return null;
        }

        Order order = new Order();
        order.setOrderedBy(user);
        Order createdOrder = orderRepository.save(order);
        createOrderItems(order, orderCreateDto.getDishes());
        updateOrderStatus(order);
        return orderMapper.orderToOrderDto(createdOrder);
    }

    @Override
    public OrderDto editOrder(OrderEditDto orderEditDto) {
        Order order = orderRepository.findById(orderEditDto.getId()).orElseThrow(() -> new RuntimeException("Order not found"));

        if (!(order.getOrderStatus().equals(OrderStatus.ORDERED) || order.getOrderStatus().equals(OrderStatus.SCHEDULED)) && orderEditDto.getOrderStatus().equals(OrderStatus.CANCELED.toString())) {
            return null;
        }
        order = orderMapper.orderEditDtoToOrder(order, orderEditDto);
        orderRepository.save(order);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto scheduleOrder(OrderScheduleDto orderScheduleDto, User user) {
        Order order = new Order();
        order.setOrderedBy(user);
        order.setOrderedAt(orderScheduleDto.getScheduledFor());
        order.setOrderStatus(OrderStatus.SCHEDULED);
        Order createdOrder = orderRepository.save(order);
        createOrderItems(order, orderScheduleDto.getDishes());
        return orderMapper.orderToOrderDto(createdOrder);
    }

    @Override
    public OrderDto fromScheduledToOrdered(Order order) {
        User user = order.getOrderedBy();
        List<OrderStatus> statuses = new ArrayList<>(List.of(OrderStatus.PREPARING, OrderStatus.IN_DELIVERY));
        if (orderRepository.numberOfOrdersInProgress(statuses) == 3) {
            Error error = new Error();
            String dishes = order.getItems().stream()
                    .map(orderItem -> orderItem.getDish().toString())
                    .collect(Collectors.joining(", "));
            error.setForOrder(dishes);
            error.setTime(LocalDateTime.now());
            error.setUser(user);
            error.setMessage("Failed to create order, there are already 3 orders in progress");
            error.setOperation("schedule");

            errorRepository.save(error);
            order.setActive(false);
            orderRepository.save(order);
            return null;
        }
        order.setOrderStatus(OrderStatus.ORDERED);
        Order createdOrder = orderRepository.save(order);
        sendOrderUpdate(order);
        updateOrderStatus(createdOrder);
        return orderMapper.orderToOrderDto(createdOrder);
    }

    private void createOrderItems(Order order, List<Dish> dishes) {
        for (Dish dish : dishes) {
            OrderItem orderItem = new OrderItem(order, dish);
            order.getItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public String getOrderStatus(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get().getOrderStatus().toString();
        } else {
            throw new RuntimeException("Order not found with ID: " + id);
        }
    }

    @Override
    public void updateOrderStatus(Order order) {
        scheduleStatusUpdate(order, OrderStatus.PREPARING, 10 + getRandomDelay(3), () ->
                scheduleStatusUpdate(order, OrderStatus.IN_DELIVERY, 15 + getRandomDelay(4), () ->
                        scheduleStatusUpdate(order, OrderStatus.DELIVERED, 20 + getRandomDelay(5), null)
                )
        );
    }

    @Async
    protected void scheduleStatusUpdate(Order order, OrderStatus nextStatus, long delayInSeconds, Runnable nextTask) {
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            order.setOrderStatus(nextStatus);
            if(nextStatus.equals(OrderStatus.DELIVERED))
                order.setActive(false);
            orderRepository.save(order);
            sendOrderUpdate(order);

            if (nextTask != null) {
                nextTask.run();
            }
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    @Async
    public void sendOrderUpdate(Order order) {
        messagingTemplate.convertAndSend("/topic/orders", new Message(order.getId(), order.getOrderStatus().toString()));
    }

    private int getRandomDelay(int maxDeviation) {
        return (int) (Math.random() * maxDeviation);
    }
}