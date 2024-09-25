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

	@GetMapping("/recent-all")
	public BaseResponse<Map<String, List<?>>> getRecentAll() {
		return BaseResponse.ok(customsMainService.getRecentAll());
	}

	@GetMapping("/recent-notices")
	public BaseResponse<List<NoticeDto>> getRecentNotices() {
		return BaseResponse.ok(customsMainService.getRecentNotices());
	}

	@GetMapping("/recent-campaigns")
	public BaseResponse<List<CampaignDto>> getRecentCampaigns() {
		return BaseResponse.ok(customsMainService.getRecentCampaigns());
	}
}
