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
 * Spring Data REST 의존성이 있을 때만 활성화되는 예외 핸들러
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(name = "org.springframework.data.rest.webmvc.RepositoryRestController")
@ControllerAdvice
public class DataRestSpecificExceptionHandler {
    // Spring Data REST 의존성이 없는 환경에서도 컴파일 에러 없이 동작하도록 리플렉션 기반 처리
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleRepositoryConstraintViolation(Exception ex, WebRequest request) {
        if (ex.getClass().getName().equals("org.springframework.data.rest.core.RepositoryConstraintViolationException")) {
            log.warn(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-RepositoryConstraintViolation", ex));
            ApiResponse<Void> response = ApiResponse.error("422", null, "요청 데이터 검증에 실패했습니다.", null);
            return ResponseEntity.status(422).body(response);
        }
        if (ex instanceof RuntimeException) throw (RuntimeException) ex;
        throw new RuntimeException(ex);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(Exception ex, WebRequest request) {
        if (ex.getClass().getName().equals("org.springframework.data.rest.webmvc.ResourceNotFoundException")) {
            log.warn(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-ResourceNotFound", ex));
            ApiResponse<Void> response = ApiResponse.error("404", null, "요청한 리소스를 찾을 수 없습니다.", null);
            return ResponseEntity.status(404).body(response);
        }
        if (ex instanceof RuntimeException) throw (RuntimeException) ex;
        throw new RuntimeException(ex);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleRepositoryRestException(Exception ex, WebRequest request) {
        if (ex.getClass().getName().equals("org.springframework.data.rest.webmvc.RepositoryRestException")) {
            log.error(ExceptionLogUtil.formatLog("DataRestApiExceptionHandler-RepositoryRestException", ex));
            ApiResponse<Void> response = ApiResponse.error("500", null, "Spring Data REST 처리 중 오류가 발생했습니다.", null);
            return ResponseEntity.status(500).body(response);
        }
        if (ex instanceof RuntimeException) throw (RuntimeException) ex;
        throw new RuntimeException(ex);
    }
}
