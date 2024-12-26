package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Dto.Response.ListUsers;
import dev.quang.identity_service.Enums.SuccessCode;
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


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    public ApiResponse<DetailUser> createUser(@RequestBody @Valid SaveUser request) {
        var response = userService.addUser(request);
        var code = SuccessCode.USER_MODIFIED;
        return ApiResponse.<DetailUser>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(response)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ListUsers>> getallUsers() {
        var response = userService.getAllsUsers();
        var code = SuccessCode.USER_RETRIVED;
        return ApiResponse.<List<ListUsers>>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DetailUser> getUser(@PathVariable("id") String id) {
        var response = userService.getUser(id); 
        var code = SuccessCode.USER_RETRIVED;
        return ApiResponse.<DetailUser>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DetailUser> updateUser(@PathVariable String id, @RequestBody SaveUser request) {
        var response = userService.updateUser(id, request);
        var code = SuccessCode.USER_MODIFIED;
        return ApiResponse.<DetailUser>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(response)
                .build();
    }

    @DeleteMapping()
    public ApiResponse<Void> deleteAll(@RequestBody(required = false) List<String> ids) {
        userService.deleteAll(ids);
        var code = SuccessCode.USER_DELETED;
        return ApiResponse.<Void>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        var code = SuccessCode.USER_DELETED;
        return ApiResponse.<Void>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    @PatchMapping()
    public ApiResponse<Void> hideAll(@RequestBody(required = false) List<String> ids) {
        userService.hideAll(ids);  
        var code = SuccessCode.USER_HIDED;      
        return ApiResponse.<Void>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> hideUser(@PathVariable("id") String id) {
        userService.hideUser(id);
        var code = SuccessCode.USER_HIDED;      
        return ApiResponse.<Void>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }
}
