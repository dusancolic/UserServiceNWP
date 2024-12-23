package com.example.UserService.repositories;

import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    OrderStatus preparing = OrderStatus.PREPARING;
//    @Query("select o from Order o where o.orderedBy.username = ?1")
//    Page<Order> findAllByUsername(Pageable pageable, String username);
    @Query("select count(o) from Order o where o.orderStatus in (:statuses)")
    Integer numberOfOrdersInProgress(@Param("statuses") List<OrderStatus> statuses);
}
