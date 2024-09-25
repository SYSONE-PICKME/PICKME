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

	@GetMapping("/list")
	public BaseResponse<List<NoticeDto>> getAllNotices(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		List<NoticeDto> notices = noticeService.getUserNotices(page, size);
		return BaseResponse.ok(notices);
	}
}
