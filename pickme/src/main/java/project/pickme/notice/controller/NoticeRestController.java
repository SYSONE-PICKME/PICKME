package project.pickme.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;

@RestController
@RequestMapping("/customs/notices")
@RequiredArgsConstructor
public class NoticeRestController {

	private final NoticeService noticeService;

	@PutMapping("/create")
	public BaseResponse<?> createNotice(@ModelAttribute NoticeDto noticeDto, @CurrentUser Customs customs) {
		Long id = noticeService.createNotice(noticeDto, customs);
		return BaseResponse.ok(id);
	}

	@PutMapping("/{id}")
	public BaseResponse<Void> updateNotice(@PathVariable("id") Long id,
		@RequestBody NoticeDto noticeDto,
		@CurrentUser Customs customs) {
		noticeService.updateNotice(noticeDto, customs);
		return BaseResponse.ok();
	}

	@DeleteMapping("/{id}")
	public BaseResponse<Void> deleteNotice(@PathVariable("id") Long id) {
		noticeService.deleteNotice(id);
		return BaseResponse.ok();
	}

}
