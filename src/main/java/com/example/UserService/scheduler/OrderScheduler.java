package com.example.UserService.scheduler;

import com.example.UserService.dtos.order.OrderDto;
import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.repositories.OrderRepository;
import com.example.UserService.services.implementation.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class OrderScheduler {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @Scheduled(cron = "0 * * * * *")
    public void processScheduledOrders() {
        LocalDateTime now = LocalDateTime.now();
        List<Order> ordersToProcess = orderRepository.findAllOrdersWithOrderStatus(OrderStatus.SCHEDULED);
        for (Order scheduledOrder : ordersToProcess) {
            if (scheduledOrder.getOrderedAt().isBefore(now) && scheduledOrder.isActive()) {
                OrderDto orderDto = orderService.fromScheduledToOrdered(scheduledOrder);
                if (orderDto != null)
                    System.out.println("Successfully ordered " + orderDto);
                else
                    System.out.println("Failed to order: " + scheduledOrder);
            }
        }
    }
}
