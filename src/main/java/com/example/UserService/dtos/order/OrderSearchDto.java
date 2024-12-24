package com.example.UserService.dtos.order;

import com.example.UserService.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderSearchDto {

   private LocalDateTime from;
   private LocalDateTime to;
   private Long userId;
   private List<OrderStatus> statuses;
}
