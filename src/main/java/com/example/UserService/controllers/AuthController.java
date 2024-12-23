package com.example.UserService.controllers;

import com.example.UserService.models.User;
import com.example.UserService.requests.LoginRequest;
import com.example.UserService.responses.LoginResponse;
import com.example.UserService.services.implementation.UserService;
import com.example.UserService.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        User user = null;
        try {
            System.out.println(loginRequest.getUsername() + " " + loginRequest.getPassword());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            System.out.println("aaa " + token.getName());
            user = userService.findUserByUsername(loginRequest.getUsername());
            authenticationManager.authenticate(token);
            System.out.println("Authenticated");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(
                new LoginResponse(
                        jwtUtil.generateToken(loginRequest.getUsername()),
                        user.isCan_create(),
                        user.isCan_read(),
                        user.isCan_update(),
                        user.isCan_delete(),
                        user.isCan_search_order(),
                        user.isCan_place_order(),
                        user.isCan_cancel_order(),
                        user.isCan_track_order(),
                        user.isCan_schedule_order(),
                        user.isAdmin()
                ));
    }

}