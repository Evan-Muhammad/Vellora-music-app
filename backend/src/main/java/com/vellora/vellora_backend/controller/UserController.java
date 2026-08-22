package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.dto.UserSummary;
import com.vellora.vellora_backend.model.User;
import com.vellora.vellora_backend.service.UserService;
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
    public UserSummary login(@RequestBody Credentials credentials) {
        User user = userService.login(credentials.username(), credentials.password());
        return UserSummary.from(user);
    }

    @PutMapping("/{id}")
    public UserSummary updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request.username(), request.email());
        return UserSummary.from(user);
    }

    public record Credentials(String username, String email, String password) {}
    public record UpdateUserRequest(String username, String email) {}
}