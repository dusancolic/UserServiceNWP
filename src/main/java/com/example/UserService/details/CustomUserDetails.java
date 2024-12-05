package com.example.UserService.details;




import com.example.UserService.models.User;

import java.util.ArrayList;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final boolean can_create;
    private final boolean can_read;
    private final boolean can_update;
    private final boolean can_delete;

    public CustomUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), new ArrayList<>());
        this.can_create = user.isCan_create();
        this.can_read = user.isCan_read();
        this.can_update = user.isCan_update();
        this.can_delete = user.isCan_delete();
    }

    public boolean canCreate() {
        return can_create;
    }

    public boolean canRead() {
        return can_read;
    }

    public boolean canUpdate() {
        return can_update;
    }

    public boolean canDelete() {
        return can_delete;
    }


}
