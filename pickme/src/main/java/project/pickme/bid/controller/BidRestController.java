package project.pickme.bid.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.MySuccessfulBidDto;
import project.pickme.bid.dto.response.BidDetailsDto;
import project.pickme.bid.dto.response.UnPaidBidDto;
import project.pickme.bid.service.BidService;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.user.domain.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/bid")
public class BidRestController {
	private static final int DEFAULT_PAGE_SIZE = 5;
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
	 * 사용자의 결제 목록을 페이지로 반환합니다.
	 *
	 * @param user 현재 로그인된 사용자 (@CurrentUser로 주입됨)
	 * @param pageable 페이지네이션 정보 (@PageableDefault로 주입됨)
	 * @return 사용자의 결제 목록을 담은 BaseResponse
	 */
	@GetMapping("/successful-list")
	public BaseResponse<?> successfulBidList(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<MySuccessfulBidDto> mySuccessfulBids = bidService.findMySuccessfulBid(user, pageable);

		return BaseResponse.ok(mySuccessfulBids);
	}

	/**
	 * 낙찰 후 미결제한 목록 조회하는 메서드입니다.
	 *
	 * @param user 로그인 된 사용자
	 * @return 미결제 목록
	 */
	@GetMapping("/unpaid")
	public BaseResponse<?> unpaidBidList(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable){
		Page<UnPaidBidDto> unPaidBidDtos = bidService.findMyUnpaidBid(user.getId(), pageable);

		return BaseResponse.ok(unPaidBidDtos);
	}
}
