package project.pickme.item.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
	private final FindItemService itemService;

	@GetMapping("/list")
	public BaseResponse<List<FindItemDto.GetAll>> getAllItems(@CurrentUser User user, @ModelAttribute ItemRequest.Cursor cursor) {
		List<FindItemDto.GetAll> items = itemService.findAll(user.getId(), cursor);

		return BaseResponse.ok(items);
	}
}
