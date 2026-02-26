

package com.github.ubisam_heung.api_response;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ConditionalOnClass(name = "org.springframework.boot.web.servlet.error.ErrorController")
@RestController
@Slf4j
public class ApiErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ApiErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiResponse<Void>> handleError(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        int status = (int) attrs.getOrDefault("status", 500);
        String path = (String) attrs.getOrDefault("path", "");
        String message = (String) attrs.getOrDefault("message", "에러가 발생했습니다.");
        String errorCode = "E" + status;
        if (status == 404) {
            message = "요청한 리소스를 찾을 수 없습니다.";
            errorCode = "E404";
        }
        // 일관된 에러 로그 포맷 출력
        log.warn(ExceptionLogUtil.formatLog("ApiErrorController", "status=" + status, message + " (path=" + path + ")"));
        ApiResponse<Void> response = ApiResponse.error(errorCode, null, message, path);
        HttpStatus httpStatus = HttpStatus.resolve(status);
        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }
}
