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
	NOT_FOUND_ITEM("존재하지 않는 공매품"),

	//입찰
	NOT_FOUND_BID("존재하지 않는 입찰"),
	BID_NOT_PROGRESS("진행중이지 않은 경매"),

	//이미지
	IMAGE_UPLOAD_FAILED("이미지 업로드 실패");

	private final String errorCode;
}
