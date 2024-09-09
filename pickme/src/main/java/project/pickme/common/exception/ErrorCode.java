package project.pickme.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND_CUSTOMS("존재하지 않는 세관"),
	NOT_FOUND_USER("존재하지 않는 회원");

	private final String errorCode;
}
