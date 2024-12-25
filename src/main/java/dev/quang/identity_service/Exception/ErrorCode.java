package dev.quang.identity_service.Exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_ERROR(999, "Undefinded exception we need more time to resolve problem"),

    USER_EXISTS(401, "User exists in system"),
    USER_NOT_EXISTS(402, "User does not exist in system"),
    ID_INVALID(403, "List id of users is invalid"),
    LENGTH_PASSWORD(404, "Password at least 5 characters"),

    KEY_INVALID(501, "Message key is invalid");
    
    int code;
    String message;

    public static boolean contains(String key) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.name().equals(key)) {
                return true;
            }
        }
        return false;
    }    
}
