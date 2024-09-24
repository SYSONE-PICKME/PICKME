package project.pickme.notice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * 새로운 공지사항을 생성
	 *
	 * @param noticeDto 생성할 공지사항의 정보를 담은 DTO
	 * @param customs 현재 로그인한 사용자 정보
	 * @return 생성된 공지사항의 ID를 포함한 BaseResponse 객체를 반환
	 */
	@PostMapping("/create")
	public BaseResponse<?> createNotice(@ModelAttribute NoticeDto noticeDto, @CurrentUser Customs customs) {
		Long id = noticeService.createNotice(noticeDto, customs);
		return BaseResponse.ok(id);
	}

	/**
	 * 기존 공지사항을 수정
	 *
	 * @param id 수정할 공지사항의 ID
	 * @param noticeDto 수정할 공지사항 정보를 담은 DTO
	 * @param customs 현재 로그인한 사용자 정보
	 * @return 수정된 공지사항에 대한 성공 응답을 반환
	 */
	@PutMapping("/{id}")
	public BaseResponse<Void> updateNotice(@PathVariable("id") Long id,
		@ModelAttribute NoticeDto noticeDto,
		@CurrentUser Customs customs) {
		noticeService.updateNotice(noticeDto, customs);
		return BaseResponse.ok();
	}

	/**
	 * 공지사항을 삭제
	 *
	 * @param id 삭제할 공지사항의 ID
	 * @return 성공적인 삭제를 알리는 BaseResponse 객체를 반환
	 */
	@DeleteMapping("/{id}")
	public BaseResponse<Void> deleteNotice(@PathVariable("id") Long id) {
		noticeService.deleteNotice(id);
		return BaseResponse.ok();
	}
}
