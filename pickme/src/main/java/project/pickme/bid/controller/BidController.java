package project.pickme.bid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.service.BidService;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BidController {
	private final BidService bidService;

	@GetMapping("/successful-bid-listForm")
	public String successfulBidListForm(@CurrentUser User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("userId", user.getId());

		return "bid/mySuccessfulBidList";
	}

	@GetMapping("/unpaid-bidForm")
	public String unpaidBidListForm(@CurrentUser User user, Model model){
		model.addAttribute("user", user);

		return "bid/unpaidBidList";
	}
}
