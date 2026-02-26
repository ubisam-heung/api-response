package com.github.ubisam_heung.api_response;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ApiError error;
    private final ErrorCode errorCode;

    public ApiException(ApiError error, ErrorCode errorCode) {
        super(error.message());
        this.error = error;
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode) {
        this(ApiError.from(errorCode), errorCode);
    }

    public ApiException(ApiError error) {
        this(error, null);
    }
}
