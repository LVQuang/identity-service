package dev.quang.identity_service.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.quang.identity_service.Dto.Request.LoginRequest;
import dev.quang.identity_service.Dto.Response.LoginResponse;
import dev.quang.identity_service.Exception.AppException;
import dev.quang.identity_service.Exception.ErrorCode;
import dev.quang.identity_service.Respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal =  true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        var result = passwordEncoder.matches(request.getPassword(), user.getPassword());
        return LoginResponse.builder()
            .result(result)
            .build();
    } 
}
