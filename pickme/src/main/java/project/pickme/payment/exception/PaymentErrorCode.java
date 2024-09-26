package project.pickme.payment.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
	INSUFFICIENT_PRICE(BAD_REQUEST, "잔액 부족");

	private final HttpStatus httpStatus;
	private final String message;
}
