package project.pickme.notice.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.service.CampaignService;
import project.pickme.user.domain.Customs;

@RestController
@RequestMapping("/customs/campaigns")
@RequiredArgsConstructor
public class CampaignRestController {

	private final CampaignService campaignService;

	@PostMapping("/create")
	public BaseResponse<?> createCampaign(@ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) throws IOException {
		Long campaignId = campaignService.createCampaign(campaignDto, customs);
		return BaseResponse.ok(campaignId);
	}

	@PutMapping("/{id}")
	public BaseResponse<?> updateCampaign(@PathVariable("id") Long id,
		@ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) throws IOException {
		campaignService.updateCampaign(campaignDto, customs);
		return BaseResponse.ok();
	}

	@DeleteMapping("/{id}")
	public BaseResponse<Void> deleteCampaign(@PathVariable("id") Long id) {
		campaignService.deleteCampaign(id);
		return BaseResponse.ok();
	}
}
