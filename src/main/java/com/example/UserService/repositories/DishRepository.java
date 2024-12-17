package com.example.UserService.repositories;

import com.example.UserService.models.Dish;
import com.example.UserService.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findByName(String name);
    @Query("SELECT d FROM Dish d WHERE d.deleted = false")
    Page<Dish> findAllExisting(Pageable pageable);
}
