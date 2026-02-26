package com.github.ubisam_heung.api_response;

public record ApiError(
    String code,
    String customCode,
    String message,
    String location
) {
    public static ApiError of(String code, String message) {
        return new ApiError(code, null, message, null);
    }
    public static ApiError of(String code, String customCode, String message, String location) {
        return new ApiError(code, customCode, message, location);
    }
        public static ApiError from(ErrorCode errorCode) {
            return new ApiError(
                errorCode.getCode(),
                null,
                errorCode.getMessage(),
                null
            );
        }
}
