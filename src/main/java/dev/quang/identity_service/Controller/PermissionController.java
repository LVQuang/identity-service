package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Dto.Request.AddPermission;
import dev.quang.identity_service.Dto.Response.ResponsePermission;
import dev.quang.identity_service.Enums.SuccessCode;
import dev.quang.identity_service.Service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    public ApiResponse<ResponsePermission> createPermission(@RequestBody AddPermission request) {
        var response = permissionService.addPermission(request);
        var code = SuccessCode.PERMISSION_CREATED;
        return ApiResponse.<ResponsePermission>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(response)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ResponsePermission>> getAllPermissions() {
        var permissions = permissionService.getAll();
        var code = SuccessCode.PERMISSION_CREATED;
        return ApiResponse.<List<ResponsePermission>>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(permissions)
                .build();
    }
    
}
