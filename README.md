# api-response

Spring Boot에서 일관된 API 응답 및 에러 처리를 위한 공용 라이브러리입니다.

## 주요 기능
- 표준화된 성공/실패 응답 구조 (`ApiResponse<T>`, `ApiError`)
- 커스텀 및 HTTP 표준 에러코드 관리 (`ErrorCode`)
- 전역 예외 처리 (`GlobalExceptionHandler`)
- Lombok 기반의 간결한 코드

## 설치 및 사용법

### 1. 의존성 추가
GitHub Packages 또는 Maven Central에 배포된 경우, 아래와 같이 `pom.xml`에 추가:

```xml
<dependency>
  <groupId>com.github.ubisam-heung</groupId>
  <artifactId>api-response</artifactId>
  <version>1.0.0</version>
</dependency>
```

> GitHub Packages 사용 시 repository 설정도 필요합니다.

### 2. 코드에서 사용
```java
import com.github.ubisam_heung.api_response.ApiResponse;
import com.github.ubisam_heung.api_response.ApiException;
import com.github.ubisam_heung.api_response.ErrorCode;

// 성공 응답
return ApiResponse.ok(data);

// 실패 응답 (예외 발생)
throw new ApiException(ErrorCode.CUSTOM_UNKNOWN_ERROR);
```

전역 예외 핸들러(`GlobalExceptionHandler`)는 자동 등록되거나, 필요시 직접 등록해 사용하세요.

## 예시 응답
성공:
```json
{
  "success": true,
  "data": { ... }
}
```
실패:
```json
{
  "success": false,
  "error": {
    "code": "C5001",
    "message": "알 수 없는 커스텀 에러입니다.",
    "path": "/api/endpoint"
  }
}
```