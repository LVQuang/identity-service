package dev.quang.identity_service.Enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SuccessCode {
    USER_MODIFIED(200, "User information modified successfully"),
    USER_RETRIVED(201, "User information retrived successfully"),
    USER_DELETED(202, "User information deleted successfully"),
    USER_HIDED(203, "User information hided successfully"),
    
    AUTHENTICATE(204, "User login to system successfully"),
    INSTROPECTED(205, "User's Token is valid");

    int code;
    String message;
}
