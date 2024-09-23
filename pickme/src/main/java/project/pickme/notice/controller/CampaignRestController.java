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
	public ResponseEntity<BaseResponse<Long>> createCampaign(@ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) {
		try {
			Long campaignId = campaignService.createCampaign(campaignDto, customs);
			return ResponseEntity.ok(BaseResponse.ok(campaignId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, null));
		}
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<BaseResponse<Void>> updateCampaign(@PathVariable("id") Long id,
		@ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) throws IOException{
			campaignService.updateCampaign(campaignDto, customs);
			return ResponseEntity.ok(BaseResponse.ok());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BaseResponse<Void>> deleteCampaign(@PathVariable("id") Long id) {
			campaignService.deleteCampaign(id);
			return ResponseEntity.ok(BaseResponse.ok());
	}
}
