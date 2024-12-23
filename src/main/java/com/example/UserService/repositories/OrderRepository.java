package com.example.UserService.repositories;

import com.example.UserService.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
//    @Query("select o from Order o where o.orderedBy.username = ?1")
//    Page<Order> findAllByUsername(Pageable pageable, String username);
}
