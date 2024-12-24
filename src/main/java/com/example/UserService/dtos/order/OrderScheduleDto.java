package com.example.UserService.dtos.order;


import com.example.UserService.models.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderScheduleDto {

    private List<Dish> dishes;
    private LocalDateTime scheduledFor;
}
