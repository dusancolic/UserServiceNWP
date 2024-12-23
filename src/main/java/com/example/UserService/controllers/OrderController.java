package com.example.UserService.controllers;

import com.example.UserService.details.CustomUserDetails;
import com.example.UserService.dtos.*;
import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.services.implementation.DishService;
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

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "5") Integer size,
                                                       @RequestParam(required = false) LocalDateTime from,
                                                       @RequestParam(required = false) LocalDateTime to,
                                                       @RequestParam(required = false) Long userId,
                                                       @RequestParam(required = false) List<OrderStatus> statuses){
        System.out.println("page: " + page + " size: " + size + " from: " + from + " to: " + to + " userId: " + userId + " statuses: " + statuses);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canSearchOrder())
            return ResponseEntity.status(403).build();
        if(!customUserDetails.isAdmin())
            userId = Long.valueOf(customUserDetails.getUser().getId());
        //TODO can search
        return ResponseEntity.ok(orderService.searchOrders(page, size, from, to, userId, statuses));
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderCreateDto orderCreateDto){
        System.out.println("order create dto dishes: " + orderCreateDto.getDishes());
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canCreate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(orderService.createOrder(orderCreateDto, customUserDetails.getUser()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrderDto> editDish(@RequestBody OrderEditDto orderEditDto){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!customUserDetails.canUpdate())
            return ResponseEntity.status(403).build();
        return new ResponseEntity<>(orderService.editOrder(orderEditDto), HttpStatus.OK);
    }

}
