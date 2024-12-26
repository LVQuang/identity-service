package dev.quang.identity_service.Enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ValidationCode {
    USER_LOGIN(304, "User login to system failed");

    int code;
    String message;
}
