package dev.quang.identity_service.Configuration;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.quang.identity_service.Dto.ApiResponse;
import dev.quang.identity_service.Exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                var errorCode = ErrorCode.UNAUTHENTICATED;

                response.setStatus(errorCode.getStatusCode().value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                var apiResponse = ApiResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build();
                
                ObjectMapper mapper = new ObjectMapper();

                response.getWriter().write(mapper.writeValueAsString(apiResponse));
                response.flushBuffer();
    }
    
}
