
# api-response

Spring/Spring Data REST 환경에서 일관된 커스텀 에러 응답과 로그를 자동으로 제공하는 경량 라이브러리입니다.

## 주요 특징
- **의존성 추가만으로 자동 적용**: 별도 설정 없이 대부분의 예외를 커스텀 포맷으로 처리
- **Spring/Spring Data REST 완벽 지원**: REST 환경의 다양한 예외를 자동으로 핸들링
- **한글 타임스탬프 및 일관된 로그 포맷**
- **확장성**: 프로젝트별 커스텀 예외도 쉽게 추가 가능

## 설치 및 적용 방법

### 1. 의존성 추가
Maven 예시:
```xml
<dependency>
  <groupId>com.github.ubisam-heung</groupId>
  <artifactId>api-response</artifactId>
  <version>최신버전</version>
</dependency>
```

### 2. 별도 설정 없이 바로 사용
- Spring Boot/Spring Data REST 환경에서 의존성만 추가하면 자동으로 커스텀 에러 응답이 적용됩니다.
- (Spring Data REST 미사용 시에도 문제없이 동작)

## 동작 방식

- **GlobalExceptionHandler**: 모든 환경에서 ApiException, Exception, 404 등 기본 예외를 커스텀 JSON으로 반환
- **DataRestApiExceptionHandler**: Spring Data REST(webmvc) 의존성 있을 때만 자동 활성화, REST 특화 예외(405, 415, 파싱 등) 처리
- **DataRestSpecificExceptionHandler**: REST 특화 예외(유효성 검증, 리소스 없음 등)를 리플렉션 기반으로 안전하게 처리
- **ExceptionLogUtil**: 모든 예외 로그를 한글 타임스탬프와 일관된 포맷으로 출력

## 커스텀 에러 응답 예시
```json
{
  "success": false,
  "data": null,
  "error": {
    "code": "C5009",
    "customCode": null,
    "message": "비즈니스 규칙 위반입니다.",
    "location": null
  }
}
```

## 확장/커스터마이징
- 프로젝트별로 추가적인 @ExceptionHandler를 자유롭게 구현해도 충돌 없이 동작합니다.
- 커스텀 ErrorCode, ApiException, ApiResponse 구조를 그대로 활용할 수 있습니다.

## 참고/권장
- Spring Data REST 환경에서는 spring-data-rest-webmvc 의존성이 필요합니다.
- ExceptionLogUtil을 활용하면 모든 예외 로그를 한글 포맷으로 통일할 수 있습니다.

---

문의/기여: [GitHub Issues](https://github.com/ubisam-heung/api-response)