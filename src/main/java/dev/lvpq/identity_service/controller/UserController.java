package dev.lvpq.identity_service.controller;

import dev.lvpq.identity_service.dto.request.UserCreationRequest;
import dev.lvpq.identity_service.dto.request.UserUpdateRequest;
import dev.lvpq.identity_service.entity.User;
import dev.lvpq.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreationRequest request) {
        return userService.createRequest(request);
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getAllRequest();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String id) {
        return userService.getByIdRequest(id);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String id, @RequestBody UserUpdateRequest request) {
        return userService.updateRequest(id, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String id) {
        userService.deleteRequest(id);
        return "User deleted";
    }
}
