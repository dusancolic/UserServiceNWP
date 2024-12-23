package com.example.UserService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "dish", nullable = false)
    private Dish dish;
    @ManyToOne(optional = false)
    @JoinColumn(name = "order1", nullable = false)
    private Order order1;

    public OrderItem(Order order, Dish dish){
        this.order1 = order;
        this.dish = dish;
    }

}

