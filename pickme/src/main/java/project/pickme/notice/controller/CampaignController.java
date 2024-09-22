package project.pickme.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.service.CampaignService;
import project.pickme.user.domain.Customs;

@Controller
@RequestMapping("/customs/campaigns")
@RequiredArgsConstructor
public class CampaignController {

	private final CampaignService campaignService;

	@GetMapping
	public String showCampaigns(Model model, @CurrentUser Customs customs) {
		List<CampaignDto> campaigns = campaignService.getAllCampaigns();
		model.addAttribute("campaigns", campaigns);
		model.addAttribute("customsId", customs.getId());
		return "notice/campaignList";
	}

	@GetMapping("/new")
	public String showNewCampaignForm(Model model, @CurrentUser Customs customs) {
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("campaign", new CampaignDto());
		model.addAttribute("isEditing", false);
		return "notice/campaignForm";
	}

	@PostMapping("/create")
	public String createCampaign(@ModelAttribute CampaignDto campaignDto, @CurrentUser Customs customs) throws
		IOException {
		Long campaignId = campaignService.createCampaign(campaignDto, customs);
		return "redirect:/customs/campaigns/" + campaignId;
	}

	@GetMapping("/edit/{id}")
	public String showEditCampaignForm(@PathVariable("id") Long id, Model model, @CurrentUser Customs customs) {
		CampaignDto campaign = campaignService.getCampaignById(id);
		model.addAttribute("campaign", campaign);
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("isEditing", true);
		return "notice/campaignForm";
	}

	@PostMapping("/edit/{id}")
	public String updateCampaign(@PathVariable("id") Long id, @ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) throws IOException {
		campaignDto = campaignDto.withId(id);
		campaignService.updateCampaign(campaignDto, customs);
		return "redirect:/customs/campaigns/" + id;
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<BaseResponse<Void>> deleteCampaign(@PathVariable("id") Long id) {
		try {
			campaignService.deleteCampaign(id);
			return ResponseEntity.ok(BaseResponse.ok());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, null));
		}
	}

	@GetMapping("/{id}")
	public String showCampaign(@PathVariable Long id, Model model, @CurrentUser Customs customs) {
		CampaignDto campaign = campaignService.getCampaignById(id);
		if (campaign == null) {
			return "redirect:/customs/campaigns";
		}
		model.addAttribute("campaign", campaign);
		model.addAttribute("customsId", customs.getId());
		return "notice/campaignContent";
	}
}
