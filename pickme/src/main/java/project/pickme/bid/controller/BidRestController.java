package project.pickme.bid.controller;

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

	/**
	 * 입찰 페이지에서 그래프, 현재 최고가에 대한 정보를 반환하는 메서드입니다.
	 *
	 * @param itemId 입찰하는 공매품 아이디
	 * @param user 로그인 된 사용자
	 * @return 입찰된 가격, 최고 입찰에 대한 정보를 담은 BidDetailsDto
	 */
	@GetMapping("/details/{itemId}")
	public BaseResponse<?> showBidDetails(@PathVariable("itemId") Long itemId, @CurrentUser User user) {
		BidDetailsDto bidDetailsDto = bidService.showBidDetails(itemId, user);

		return BaseResponse.ok(bidDetailsDto);
	}

	/**
	 * 낙찰하는 메서드입니다.
	 *
	 * @param selectedBidDto 낙찰된 입찰에 대한 정보를 담은 dto
	 * @return 성공여부
	 * @throws MessagingException
	 */
	@PostMapping("/end")
	public BaseResponse<?> endBid(@RequestBody SelectedBidDto selectedBidDto) throws MessagingException {
		bidService.selectBid(selectedBidDto);

		return BaseResponse.ok();
	}
}
