package project.pickme.bid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.service.BidService;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.bid.dto.response.OneBidItemDto;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BidController {
	private final BidService bidService;

	//TODO: 연결 해제 후 다시 했을때 고려해서 REST로 변경 해야할듯
	@GetMapping("/bid/{itemId}")
	public String show(@CurrentUser User user, @PathVariable("itemId") Long itemId, Model model) {
		OneBidItemDto oneBidItemDto = bidService.showOneBidItem(user, itemId);
		model.addAttribute("oneBidItemDto", oneBidItemDto);

		return "bid/bidPage";
	}

	@GetMapping("/successful-bid-list")
	public String successfulBidList(@CurrentUser User user, Model model) {
		model.addAttribute("successfulBidList", bidService.findMySuccessfulBid(user));

		return "bid/mySuccessfulBidList";
	}
}
