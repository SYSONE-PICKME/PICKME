package project.pickme.notice.controller;

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

	@GetMapping("/edit/{id}")
	public String showEditCampaignForm(@PathVariable("id") Long id, Model model, @CurrentUser Customs customs) {
		CampaignDto campaign = campaignService.getCampaignById(id);
		model.addAttribute("campaign", campaign);
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("isEditing", true);
		return "notice/campaignForm";
	}

	@GetMapping("/{id}")
	public String showCampaign(@PathVariable Long id, Model model, @CurrentUser Customs customs) {
		CampaignDto campaign = campaignService.getCampaignById(id);
		model.addAttribute("campaign", campaign);
		model.addAttribute("customsId", customs.getId());
		return "notice/campaignContent";
	}
}
