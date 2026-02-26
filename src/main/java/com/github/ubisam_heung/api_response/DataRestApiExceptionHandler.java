package com.github.ubisam_heung.api_response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Data REST용 커스텀 예외 핸들러
 * (api-response 의존성만 추가해도 동작하도록 구성)
 */

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(name = "org.springframework.data.rest.webmvc.RepositoryRestController")
@ControllerAdvice
public class DataRestApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex, WebRequest request) {
        log.error(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-ApiException", ex.getClass().getSimpleName(), ex.getError().message()));
        ApiResponse<Void> response = ApiResponse.error(ex.getError());
        int status = ex.getErrorCode() != null ? ex.getErrorCode().getHttpStatus() : 400;
        if (status < 100 || status > 599) {
            status = 500;
        } else if (status >= 1000) {
            status = 500;
        }
        return ResponseEntity.status(status).body(response);
    }

    // 지원하지 않는 HTTP 메서드
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException ex, WebRequest request) {
        log.warn(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-MethodNotSupported", ex));
        ApiResponse<Void> response = ApiResponse.error("405", null, "허용되지 않은 HTTP 메서드입니다.", null);
        return ResponseEntity.status(405).body(response);
    }

    // 지원하지 않는 미디어 타입
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(org.springframework.web.HttpMediaTypeNotSupportedException ex, WebRequest request) {
        log.warn(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-MediaTypeNotSupported", ex));
        ApiResponse<Void> response = ApiResponse.error("415", null, "지원하지 않는 미디어 타입입니다.", null);
        return ResponseEntity.status(415).body(response);
    }

    // 요청 본문 파싱 실패
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException ex, WebRequest request) {
        log.warn(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-MessageNotReadable", ex));
        ApiResponse<Void> response = ApiResponse.error("400", null, "요청 본문을 읽을 수 없습니다.", null);
        return ResponseEntity.status(400).body(response);
    }

    // Spring Data REST의 기타 예외도 필요시 추가 핸들링 가능
}
