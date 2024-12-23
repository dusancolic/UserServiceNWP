package com.example.UserService.scheduler;

import com.example.UserService.models.Order;
import com.example.UserService.repositories.OrderRepository;
import com.example.UserService.services.implementation.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class OrderScheduler {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @Scheduled(cron = "0 * * * * *")
    public void processScheduledOrders() {
//        LocalDateTime now = LocalDateTime.now();
//        List<Order> ordersToProcess = orderRepository.findAll();
//
//        for (Order scheduledOrder : ordersToProcess) {
//
//            //orderService.createOrder();
//            orderRepository.save(order);
//
//
//        }
        System.out.println("a minute has passed");
    }
}
