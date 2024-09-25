package project.pickme.notice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.CustomsMainService;

@RestController
@RequestMapping("/customs/main")
@RequiredArgsConstructor
public class CustomsMainRestController {

	private final CustomsMainService customsMainService;

	/**
	 * 최근 공지사항과 캠페인을 함께 조회합니다.
	 * 최신 기준 6개의 공지사항과 캠페인 반환
	 *
	 * @return "notices"와 "campaigns" 키를 가진 Map이며
	 * 			각각의 값은 NoticeDto와 CampaignDto의 리스트
	 */
	@GetMapping("/recent-all")
	public BaseResponse<Map<String, List<?>>> getRecentAll() {
		return BaseResponse.ok(customsMainService.getRecentAll());
	}

	/**
	 * 최근 공지사항만을 조회
	 * 최신 6개의 공지사항을 반환
	 *
	 * @return BaseResponse 객체에 감싸진 NoticeDto 리스트
	 */
	@GetMapping("/recent-notices")
	public BaseResponse<List<NoticeDto>> getRecentNotices() {
		return BaseResponse.ok(customsMainService.getRecentNotices());
	}

	/**
	 * 최근 캠페인만을 조회
	 * 최신 6개의 캠페인을 반환
	 *
	 * @return BaseResponse 객체에 감싸진 CampaignDto 리스트
	 */
	@GetMapping("/recent-campaigns")
	public BaseResponse<List<CampaignDto>> getRecentCampaigns() {
		return BaseResponse.ok(customsMainService.getRecentCampaigns());
	}
}
