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

	/**
	 * 새로운 공지사항을 생성합니다.
	 *
	 * @param noticeDto 생성할 공지사항의 정보를 담은 DTO입니다.
	 * @param customs 현재 로그인한 사용자 정보입니다.
	 * @return 생성된 공지사항의 ID를 포함한 BaseResponse 객체를 반환합니다.
	 */
	@PutMapping("/create")
	public BaseResponse<?> createNotice(@ModelAttribute NoticeDto noticeDto, @CurrentUser Customs customs) {
		Long id = noticeService.createNotice(noticeDto, customs);
		return BaseResponse.ok(id);
	}

	/**
	 * 기존 공지사항을 수정합니다.
	 *
	 * @param id 수정할 공지사항의 ID입니다.
	 * @param noticeDto 수정할 공지사항 정보를 담은 DTO입니다.
	 * @param customs 현재 로그인한 사용자 정보입니다.
	 * @return 수정된 공지사항에 대한 성공 응답을 반환합니다.
	 */
	@PutMapping("/{id}")
	public BaseResponse<Void> updateNotice(@PathVariable("id") Long id,
		@RequestBody NoticeDto noticeDto,
		@CurrentUser Customs customs) {
		noticeService.updateNotice(noticeDto, customs);
		return BaseResponse.ok();
	}

	/**
	 * 공지사항을 삭제합니다.
	 *
	 * @param id 삭제할 공지사항의 ID입니다.
	 * @return 성공적인 삭제를 알리는 BaseResponse 객체를 반환합니다.
	 */
	@DeleteMapping("/{id}")
	public BaseResponse<Void> deleteNotice(@PathVariable("id") Long id) {
		noticeService.deleteNotice(id);
		return BaseResponse.ok();
	}
}
