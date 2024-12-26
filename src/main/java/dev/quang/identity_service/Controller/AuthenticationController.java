package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Dto.Request.InstropectRequest;
import dev.quang.identity_service.Dto.Request.LoginRequest;
import dev.quang.identity_service.Dto.Response.InstropectResponse;
import dev.quang.identity_service.Dto.Response.LoginResponse;
import dev.quang.identity_service.Enums.SuccessCode;
import dev.quang.identity_service.Service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

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
        var code = SuccessCode.AUTHENTICATE;
        return ApiResponse.<LoginResponse>builder()
            .code(code.getCode())
            .data(response)
            .message(code.getMessage())
            .build();
    }

    @PostMapping("/instro")
    public ApiResponse<InstropectResponse> instropect(@RequestBody InstropectRequest request) 
            throws ParseException, JOSEException {
        var response = authenticationService.instropect(request);
        var code = SuccessCode.INSTROPECTED;
        return ApiResponse.<InstropectResponse>builder()
            .code(code.getCode())
            .data(response)
            .message(code.getMessage())
            .build();
    }
    
}
