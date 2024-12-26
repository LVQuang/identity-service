package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Dto.Request.LoginRequest;
import dev.quang.identity_service.Dto.Response.LoginResponse;
import dev.quang.identity_service.Enums.SuccessCode;
import dev.quang.identity_service.Service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping()
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        var response = authenticationService.login(request);
        SuccessCode code;
        if (response.isResult()) {
            code = SuccessCode.USER_LOGIN;
        } else {
            code = SuccessCode.USER_LOGIN_FAILED;
        }
        return ApiResponse.<LoginResponse>builder()
            .code(code.getCode())
            .data(response)
            .message(code.getMessage())
            .build();
    }
    
}
