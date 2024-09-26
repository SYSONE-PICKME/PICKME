package project.pickme.item.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.dto.ItemRequest;
import project.pickme.item.service.FindItemService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user/api/item")
@RequiredArgsConstructor
public class FindUserItemRestController {
	private final static int DEFAULT_PAGE_SIZE = 5;
	private final FindItemService itemService;

	/**
	 * 모든 아이템 목록을 조회하는 메서드입니다.
	 *
	 * 이 메서드는 현재 사용자의 정보와 페이징 및 필터링을 위한 커서 정보를 받아,
	 * 아이템 목록을 조회하여 반환합니다.
	 *
	 * @param user 현재 인증된 사용자 정보
	 * @param cursor 아이템 목록 조회 시 페이징 및 필터링을 위한 {@link ItemRequest.Cursor} 객체
	 * @return 조회된 아이템 목록을 담고 있는 {@link BaseResponse} 객체를 반환
	 */
	@GetMapping("/list")
	public BaseResponse<List<FindItemDto.GetAll>> getAllItems(@CurrentUser User user, @ModelAttribute ItemRequest.Cursor cursor) {
		List<FindItemDto.GetAll> items = itemService.findAll(user.getId(), cursor);

		return BaseResponse.ok(items);
	}

	/**
	 * 사용자의 위시 리스트를 조회하는 메서드입니다.
	 *
	 * 이 메서드는 현재 사용자의 정보와 페이지네이션 정보를 받아,
	 * 사용자가 위시 리스트에 추가한 아이템 목록을 조회하여 반환합니다.
	 *
	 * @param user 현재 인증된 사용자 정보
	 * @param pageable 페이지네이션 정보를 담고 있는 {@link Pageable} 객체
	 * @return 조회된 위시 리스트 아이템 목록을 {@link Page} 형태로 담고 있는 {@link BaseResponse} 객체를 반환
	 */
	@GetMapping("/wish-list")
	public BaseResponse<?> getWishList(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
		Page<FindItemDto.WishList> wishList = itemService.findWishList(user.getId(), pageable);

		return BaseResponse.ok(wishList);
	}

	/**
	 * 사용자가 입찰한 아이템 목록을 조회하는 메서드입니다.
	 *
	 * 이 메서드는 현재 사용자의 정보와 입찰 목록 조회를 위한 커서 정보를 받아,
	 * 사용자가 입찰한 아이템 목록을 조회하여 반환합니다.
	 *
	 * @param user 현재 인증된 사용자 정보
	 * @param cursor 입찰 목록 조회 시 페이징 및 필터링을 위한 {@link ItemRequest.BidCursor} 객체
	 * @return 조회된 입찰 아이템 목록을 담고 있는 {@link BaseResponse} 객체를 반환
	 */
	@GetMapping("/bid-list")
	public BaseResponse<List<FindItemDto.MyBid>> getBidList(@CurrentUser User user, @ModelAttribute ItemRequest.BidCursor cursor) {
		System.out.println("Request~~");
		List<FindItemDto.MyBid> bidList = itemService.findBidList(user.getId(), cursor);

		return BaseResponse.ok(bidList);
	}
}
