package com.example.UserService.dtos.order;

import com.example.UserService.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private User orderedBy;
    private String orderStatus;
    private boolean active;
    private LocalDateTime orderedAt;


    @Override
    public String toString(){
        return "id: " + id + " orderedBy: " + orderedBy.getUsername() + " status: " +
                orderStatus + " active: " + active + " ordered at: " + orderedAt;
    }

}
