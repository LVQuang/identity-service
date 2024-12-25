package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Dto.Response.ListUsers;
import dev.quang.identity_service.Service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    public ApiResponse<DetailUser> createUser(@RequestBody @Valid SaveUser request) {
        var response = userService.addUser(request);
        return ApiResponse.<DetailUser>builder()
                .code(201)
                .message("User created")
                .data(response)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ListUsers>> getallUsers() {
        var response = userService.getAllsUsers();
        return ApiResponse.<List<ListUsers>>builder()
                .code(200)
                .message("Users list render for more information")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DetailUser> getUser(@PathVariable("id") String id) {
        var response = userService.getUser(id); 
        return ApiResponse.<DetailUser>builder()
                .code(200)
                .message("User retrived for detail information")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DetailUser> updateUser(@PathVariable String id, @RequestBody SaveUser request) {
        var response = userService.updateUser(id, request);;
        return ApiResponse.<DetailUser>builder()
                .code(202)
                .message("User information updated")
                .data(response)
                .build();
    }

    @DeleteMapping()
    public ApiResponse<Void> deleteAll(@RequestBody(required = false) List<String> ids) {
        userService.deleteAll(ids);
        return ApiResponse.<Void>builder()
                .code(203)
                .message("Users deleted")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .code(203)
                .message("User deleted")
                .build();
    }

    @PatchMapping()
    public ApiResponse<Void> hideAll(@RequestBody(required = false) List<String> ids) {
        userService.hideAll(ids);        
        return ApiResponse.<Void>builder()
                .code(203)
                .message("Users hided")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> hideUser(@PathVariable("id") String id) {
        userService.hideUser(id);
        return ApiResponse.<Void>builder()
                .code(203)
                .message("User hided")
                .build();
    }
}
