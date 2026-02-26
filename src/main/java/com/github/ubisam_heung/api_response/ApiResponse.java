package com.github.ubisam_heung.api_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ApiError error;
    
    // 성공 응답 생성
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // 실패 응답 생성 (에러코드, 메시지)
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, null, ApiError.of(code, message));
    }

    // 실패 응답 생성 (ApiError)
    public static <T> ApiResponse<T> error(ApiError error) {
        return new ApiResponse<>(false, null, error);
    }

    // 실패 응답 생성 (에러코드, 커스텀코드, 메시지, 위치)
    public static <T> ApiResponse<T> error(String code, String customCode, String message, String location) {
        return new ApiResponse<>(false, null, ApiError.of(code, customCode, message, location));
    }
}
