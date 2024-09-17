package project.pickme.bid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.reqeust.SelectedBidDto;
import project.pickme.bid.dto.response.BidDetailsDto;
import project.pickme.bid.service.BidService;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.user.domain.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/bid")
public class BidRestController {

	private final BidService bidService;

	@GetMapping("/details/{itemId}")
	public ResponseEntity<?> showBidDetails(@PathVariable("itemId") Long itemId, @CurrentUser User user){
		BidDetailsDto bidDetailsDto = bidService.showBidDetails(itemId, user);

		return ResponseEntity.ok(BaseResponse.ok(bidDetailsDto));
	}

	@PostMapping("/end")
	public ResponseEntity<?> endBid(@RequestBody SelectedBidDto selectedBidDto) throws MessagingException {
		bidService.selectBid(selectedBidDto);

		return ResponseEntity.ok("공매 종료");
	}
}
