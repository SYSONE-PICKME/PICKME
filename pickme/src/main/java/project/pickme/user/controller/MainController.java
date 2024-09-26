package project.pickme.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.service.FindItemService;
import project.pickme.notice.service.CampaignService;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final FindItemService itemService;
	private final CampaignService campaignService;

	@GetMapping("/user/main")
	public String showMain(Model model) {
		List<FindItemDto.GetAll> items = itemService.findTop20();
		List<String> campaigns = campaignService.get4Campaigns();
		model.addAttribute("items", items);
		model.addAttribute("campaigns", campaigns);
		return "index";
	}

	@GetMapping("/customs/main")
	public String showMain(@CurrentUser Customs customs, Model model) {
		return "customs/customsMain";
	}
}
