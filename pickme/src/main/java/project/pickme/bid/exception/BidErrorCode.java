package project.pickme.bid.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public enum BidErrorCode implements ErrorCode {
	NOT_FOUND_BID(NOT_FOUND, "존재하지 않는 입찰"),
	BID_NOT_PROGRESS(BAD_REQUEST, "진행중이지 않은 경매");

	private final HttpStatus httpStatus;
	private final String message;
}
