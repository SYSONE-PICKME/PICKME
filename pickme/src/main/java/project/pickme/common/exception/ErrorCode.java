package project.pickme.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	//사용자
	NOT_FOUND_CUSTOMS(NOT_FOUND,"존재하지 않는 세관"),
	NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 회원"),
	DUPLICATE_ID(BAD_REQUEST,"이미 존재하는 아이디"),

	//공매품
	NOT_FOUND_ITEM(NOT_FOUND,"존재하지 않는 공매품"),

	//입찰
	NOT_FOUND_BID(NOT_FOUND, "존재하지 않는 입찰"),
	BID_NOT_PROGRESS(BAD_REQUEST,"진행중이지 않은 경매"),

	//이미지
	IMAGE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, "이미지 업로드 실패"),

	//webSocket
	INVALID_JSON_DATA(INTERNAL_SERVER_ERROR, "데이터 JSON 변환 에러");

	private final HttpStatus status;
	private final String errorMessage;
}
