package com.example.UserService.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean can_read = false;
    private boolean can_create = false;
    private boolean can_update = false;
    private boolean can_delete = false;
    private boolean can_search_order = false;
    private boolean can_place_order = false;
    private boolean can_cancel_order = false;
    private boolean can_track_order = false;
    private boolean can_schedule_order = false;
    private boolean deleted = false;
    private boolean admin = false;
}
