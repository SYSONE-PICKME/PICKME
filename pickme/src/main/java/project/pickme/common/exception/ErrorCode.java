package project.pickme.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	//사용자
	NOT_FOUND_CUSTOMS("존재하지 않는 세관"),
	NOT_FOUND_USER("존재하지 않는 회원"),

	//공매품
	NOT_FOUND_ITEM("존재하지 않는 공매품");

	private final String errorCode;
}
