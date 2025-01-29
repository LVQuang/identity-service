package dev.quang.identity_service.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.quang.identity_service.Dto.ApiResponse;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> handleDefaultException(Exception exception) {
        var error = ErrorCode.UNCATEGORIZED_ERROR;

        exception.printStackTrace();

        return ResponseEntity.status(error.getStatusCode())
                .body(ApiResponse.<Void>builder()
                    .code(error.getCode())
                    .message(error.getMessage())    
                    .build());
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        var error = exception.getError();

        return ResponseEntity.status(error.getStatusCode())
                .body(ApiResponse.<Void>builder()
                    .code(error.getCode())
                    .message(error.getMessage())    
                    .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class) 
    ResponseEntity<ApiResponse<Void>> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        var error = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(error.getStatusCode())
                .body(ApiResponse.<Void>builder()
                    .code(error.getCode())
                    .message(error.getMessage())    
                    .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException exception) {
        var fieldError = exception.getFieldError();
        var enumKey = (fieldError != null) 
            ? fieldError.getDefaultMessage() 
            : "KEY_INVALID"; 
        
            var error = ErrorCode.contains(enumKey) 
            ? ErrorCode.valueOf(enumKey) 
            : ErrorCode.valueOf("KEY_INVALID");

        return ResponseEntity.status(error.getStatusCode())
                .body(ApiResponse.<Void>builder()
                    .code(error.getCode())
                    .message(error.getMessage())    
                    .build());
    }
}
