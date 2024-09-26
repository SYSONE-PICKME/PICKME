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
import project.pickme.item.service.FindItemService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user/api/item")
@RequiredArgsConstructor
public class FindUserItemRestController {
	private final static int DEFAULT_PAGE_SIZE = 5;
	private final FindItemService itemService;

	@GetMapping("/wish-list")
	public BaseResponse<?> getWishList(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
		Page<FindItemDto.WishList> wishList = itemService.findWishList(user.getId(), pageable);

		return BaseResponse.ok(wishList);
	}

	@GetMapping("/list")
	public BaseResponse<List<FindItemDto.GetAll>> getAllItems(@CurrentUser User user, @ModelAttribute ItemRequest.Cursor cursor) {
		List<FindItemDto.GetAll> items = itemService.findAll(user.getId(), cursor);

		return BaseResponse.ok(items);
	}
}
