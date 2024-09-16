package project.pickme.item.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {
	NOT_FOUND_ITEM(NOT_FOUND, "존재하지 않는 공매품");

	private final HttpStatus httpStatus;
	private final String message;
}
