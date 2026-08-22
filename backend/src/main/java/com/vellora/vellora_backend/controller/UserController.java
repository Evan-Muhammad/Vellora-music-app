package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.dto.UserSummary;
import com.vellora.vellora_backend.model.User;
import com.vellora.vellora_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserSummary register(@RequestBody Credentials credentials) {
        User user = userService.register(credentials.username(), credentials.email(), credentials.password());
        return UserSummary.from(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        try {
            User user = userService.login(credentials.username(), credentials.password());
            return ResponseEntity.ok(UserSummary.from(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            User user = userService.updateUser(id, request.username(), request.email());
            return ResponseEntity.ok(UserSummary.from(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public record Credentials(String username, String email, String password) {}
    public record UpdateUserRequest(String username, String email) {}
}