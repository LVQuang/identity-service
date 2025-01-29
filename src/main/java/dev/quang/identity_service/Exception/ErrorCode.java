package dev.quang.identity_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_ERROR(999, "Undefinded exception we need more time to resolve problem", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTS(401, "User exists in system", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(402, "User does not exist in system", HttpStatus.BAD_REQUEST),
    ID_INVALID(403, "List id of users is invalid", HttpStatus.BAD_REQUEST),
    LENGTH_PASSWORD(404, "Password at least 5 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_WRONG(405, "Your password are not correct", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(406, "Your token is invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(407, "Your account is unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(408, "Your account doesn't have permissions", HttpStatus.FORBIDDEN),


    KEY_INVALID(501, "Message key is invalid", HttpStatus.BAD_REQUEST);
    
    int code;
    String message;
    HttpStatusCode statusCode;

    public static boolean contains(String key) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.name().equals(key)) {
                return true;
            }
        }
        return false;
    }    
}
