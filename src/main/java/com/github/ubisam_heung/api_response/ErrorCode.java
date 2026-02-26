package com.github.ubisam_heung.api_response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 4xx Client Errors
	BAD_REQUEST(400, "E400", "잘못된 요청입니다."),
	UNAUTHORIZED(401, "E401", "인증이 필요합니다."),
	FORBIDDEN(403, "E403", "접근이 거부되었습니다."),
	NOT_FOUND(404, "E404", "요청한 리소스를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(405, "E405", "허용되지 않은 HTTP 메서드입니다."),
	CONFLICT(409, "E409", "이미 존재하거나 충돌이 발생했습니다."),
	UNSUPPORTED_MEDIA_TYPE(415, "E415", "지원하지 않는 미디어 타입입니다."),
	VALIDATION_ERROR(422, "E422", "요청 데이터 검증에 실패했습니다."),

	// 5xx Server Errors
	INTERNAL_SERVER_ERROR(500, "E500", "서버 내부 오류가 발생했습니다."),
	NOT_IMPLEMENTED(501, "E501", "구현되지 않은 기능입니다."),
	BAD_GATEWAY(502, "E502", "잘못된 게이트웨이입니다."),
	SERVICE_UNAVAILABLE(503, "E503", "서비스를 사용할 수 없습니다."),
	GATEWAY_TIMEOUT(504, "E504", "게이트웨이 타임아웃입니다."),

	// 5000번대 커스텀 에러
	CUSTOM_UNKNOWN_ERROR(5001, "C5001", "알 수 없는 커스텀 에러입니다."),
	USER_NOT_FOUND(5002, "C5002", "사용자를 찾을 수 없습니다."),
	INVALID_USER_STATUS(5003, "C5003", "유효하지 않은 사용자 상태입니다."),
	POINT_NOT_ENOUGH(5004, "C5004", "포인트가 부족합니다."),
	DUPLICATE_REQUEST(5005, "C5005", "중복된 요청입니다."),
	EXTERNAL_API_FAIL(5006, "C5006", "외부 API 호출 실패입니다."),
	FILE_UPLOAD_ERROR(5007, "C5007", "파일 업로드에 실패했습니다."),
	DATA_INTEGRITY_ERROR(5008, "C5008", "데이터 무결성 오류입니다."),
	BUSINESS_RULE_VIOLATION(5009, "C5009", "비즈니스 규칙 위반입니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}
