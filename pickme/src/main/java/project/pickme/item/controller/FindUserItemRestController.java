package project.pickme.item.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.service.FindItemService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class FindUserItemRestController {
	private final static int DEFAULT_PAGE_SIZE = 5;
	private final FindItemService itemService;

	@GetMapping("/wish-list")
	public BaseResponse<?> getWishList(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
		Page<FindItemDto.WishList> wishList = itemService.findWishList(user.getId(), pageable);

		return BaseResponse.ok(wishList);
	}
}
