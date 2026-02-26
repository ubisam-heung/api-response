
package com.github.ubisam_heung.api_response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 기본 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error(ExceptionLogUtil.formatLog("GlobalExceptionHandler-Exception", ex));
        ApiResponse<Void> response = ApiResponse.error("500", null, "서버 내부 오류가 발생했습니다.", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 없는 요청(404) 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoHandlerFoundException ex) {
        log.warn(ExceptionLogUtil.formatLog("GlobalExceptionHandler-404", ex.getClass().getSimpleName(), ex.getRequestURL()));
        ApiResponse<Void> response = ApiResponse.error("404", null, "요청한 리소스를 찾을 수 없습니다.", ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 커스텀 예외 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        log.error(ExceptionLogUtil.formatLog("GlobalExceptionHandler-ApiException", ex.getClass().getSimpleName(), ex.getError().message()));
        ApiResponse<Void> response = ApiResponse.error(ex.getError());
        int status = ex.getErrorCode() != null ? ex.getErrorCode().getHttpStatus() : 400;
        // HTTP status code는 반드시 3자리여야 하므로, 5000번대 등 커스텀 코드는 500으로 매핑
        if (status < 100 || status > 599) {
            status = 500;
        } else if (status >= 1000) {
            // 1000 이상은 HTTP 표준이 아니므로 500으로 처리
            status = 500;
        }
        return ResponseEntity.status(status).body(response);
    }
}
