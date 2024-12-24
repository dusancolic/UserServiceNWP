package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.order.*;
import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.services.implementation.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

  /*  @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrders(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size){
        System.out.println("page: " + page + " size: " + size);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canSearchOrder())
            return ResponseEntity.status(403).build();

        if(customUserDetails.isAdmin())
            return ResponseEntity.ok(orderService.findAll(page, size));
        return ResponseEntity.ok(orderService.findAllByUsername(page, size, customUserDetails.getUsername()));
    }*/

    @PostMapping("/search")
    public ResponseEntity<Page<OrderDto>> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "5") Integer size,
                                                       @RequestBody OrderSearchDto orderSearchDto){
        System.out.println("page: " + page + " size: " + size + " from: " + orderSearchDto.getFrom() + " to: " + orderSearchDto.getTo() + " userId: " + orderSearchDto.getUserId()
                + " statuses: " + orderSearchDto.getStatuses());
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canSearchOrder())
            return ResponseEntity.status(403).build();
        if(!customUserDetails.isAdmin())
            orderSearchDto.setUserId(Long.valueOf(customUserDetails.getUser().getId()));
        return ResponseEntity.ok(orderService.searchOrders(page, size, orderSearchDto));
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderCreateDto orderCreateDto){
        System.out.println("order create dto dishes: " + orderCreateDto.getDishes());
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canPlaceOrder())
            return ResponseEntity.status(403).build();
        OrderDto orderDto = orderService.createOrder(orderCreateDto, customUserDetails.getUser());
        if(orderDto != null)
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        return ResponseEntity.status(429).build();
    }

    @PostMapping("/schedule")
    public ResponseEntity<OrderDto> scheduleOrder(@RequestBody OrderScheduleDto orderScheduleDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!customUserDetails.canScheduleOrder())
            return ResponseEntity.status(403).build();
        OrderDto orderDto = orderService.scheduleOrder(orderScheduleDto, customUserDetails.getUser());

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrderDto> editOrder(@RequestBody OrderEditDto orderEditDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canCancelOrder())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(orderService.editOrder(orderEditDto), HttpStatus.OK);
    }


}
