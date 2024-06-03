package dev.lvpq.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_ERROR(9999, "Uncategorized error"),
    USER_EXISTS(1001, "User exists"),
    USER_NOT_EXISTS(1002, "User don't exists"),
    INVALID_USERNAME(1003, "Username must at least 5 characters"),
    INVALID_PASSWORD(1004, "Password must at least 8 characters"),
    INVALID_KEY(1005, "Enum key of Error code invalid")
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
