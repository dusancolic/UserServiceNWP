package com.example.UserService.specification;

import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> withDateFrom(LocalDateTime dateFrom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("orderedAt"), dateFrom);
    }

    public static Specification<Order> withDateTo(LocalDateTime dateTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("orderedAt"), dateTo);
    }

    public static Specification<Order> byUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("orderedBy").get("id"), userId);
    }

    public static Specification<Order> withStatuses(List<OrderStatus> statuses) {
        return (root, query, criteriaBuilder) ->
                root.get("orderStatus").in(statuses);
    }
}
