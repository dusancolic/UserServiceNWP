package com.example.UserService.repositories;

import com.example.UserService.models.Error;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {
    @Query("select e from Error e where e.user.username = ?1")
    Page<Error> findAllByUsername(Pageable pageable, String username);
}
