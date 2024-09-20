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

	@GetMapping("/successful-bid-list")
	public String successfulBidList(@CurrentUser User user, Model model) {
		model.addAttribute("successfulBidList", bidService.findMySuccessfulBid(user));

		return "bid/mySuccessfulBidList";
	}
}
