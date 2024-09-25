package project.pickme.notice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;

@RestController
@RequestMapping("/user/notices")
@RequiredArgsConstructor
public class UserNoticeRestController {

	private final NoticeService noticeService;

	/**
	 * 유저들이 보는 공지사항을 페이징하여 조회
	 *
	 * @param page 조회할 페이지 번호 (0부터 시작)
	 * @param size 한 페이지당 조회할 공지사항 개수 (기본값 10)
	 * @return 공지사항 목록을 포함한 BaseResponse 객체
	 */
	@GetMapping("/list")
	public BaseResponse<List<NoticeDto>> getAllNotices(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		List<NoticeDto> notices = noticeService.getUserNotices(page, size);
		return BaseResponse.ok(notices);
	}
}
