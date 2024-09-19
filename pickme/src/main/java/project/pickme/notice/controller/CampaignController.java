package project.pickme.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.service.CampaignService;
import project.pickme.user.domain.Customs;

@Controller
@RequestMapping("/customs/campaigns")
@RequiredArgsConstructor
public class CampaignController {

	private final CampaignService campaignService;

	@GetMapping
	public String showCampaigns(Model model) {
		List<CampaignDto> campaigns = campaignService.getAllCampaigns();
		model.addAttribute("campaigns", campaigns);
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
	public String createCampaign(@ModelAttribute CampaignDto campaignDto, @CurrentUser Customs customs) throws IOException {
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
	public String updateCampaign(@PathVariable("id") Long id, @ModelAttribute CampaignDto campaignDto, @CurrentUser Customs customs) throws IOException {
		campaignDto = campaignDto.withId(id);
		campaignService.updateCampaign(campaignDto, customs);
		return "redirect:/customs/campaigns/" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteCampaign(@PathVariable("id") Long id) {
		try {
			campaignService.deleteCampaign(id);
			return "redirect:/customs/campaigns";
		} catch (Exception e) {
			return "redirect:/customs/campaigns?error=deletefailed";		//todo : AJAX시도할 때 이상한 값 넘어와서 걸어뒀는데 AJAX로 바꿀 때 없애기
		}
	}

	@GetMapping("/{id}")
	public String showCampaign(@PathVariable Long id, Model model) {
		CampaignDto campaign = campaignService.getCampaignById(id);
		if (campaign == null) {
			return "redirect:/customs/campaigns";
		}
		model.addAttribute("campaign", campaign);
		return "notice/campaignContent";
	}
}
