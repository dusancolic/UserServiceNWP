package com.example.UserService.details;




import com.example.UserService.models.User;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {


    private final User user;

    public CustomUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), new ArrayList<>());
        this.user = user;

    }

    public boolean canCreate() {
        return user.isCan_create();
    }

    public boolean canRead() {
        return user.isCan_read();
    }

    public boolean canUpdate() {
        return user.isCan_update();
    }

    public boolean canDelete() {
        return user.isCan_delete();
    }

    public boolean isAdmin() {
        return user.isAdmin();
    }

    public boolean canSearchOrder() {
        return user.isCan_search_order();
    }

    public boolean canPlaceOrder() {
        return user.isCan_place_order();
    }

    public boolean canCancelOrder() {
        return user.isCan_cancel_order();
    }

    public boolean canTrackOrder() {
        return user.isCan_track_order();
    }

    public boolean canScheduleOrder() {
        return user.isCan_schedule_order();
    }

}
