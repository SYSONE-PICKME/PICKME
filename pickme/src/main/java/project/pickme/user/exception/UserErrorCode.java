package project.pickme.user.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
	NOT_FOUND_CUSTOMS(NOT_FOUND, "존재하지 않는 세관"),
	NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 회원"),
	DUPLICATE_ID(BAD_REQUEST, "이미 존재하는 아이디");

	private final HttpStatus httpStatus;
	private final String message;
}
