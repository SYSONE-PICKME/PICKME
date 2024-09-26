package project.pickme.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.item.service.FindItemService;
import project.pickme.user.domain.User;
import project.pickme.user.service.UserService;

@Controller
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class FindUserItemController {
	private final FindItemService itemService;
	private final UserService userService;

	@GetMapping("/{id}")
	public String getItemInfo(@CurrentUser User user, @PathVariable("id") Long id, Model model) {
		FindItemDto.Info item = itemService.findById(id, user.getId());
		model.addAttribute("item", item);

		return "item/item";
	}

	@GetMapping("/list")
	public String getAllItemsForm() {
		return "item/list";
	}

	@GetMapping("/bid/{id}")
	public String show(@CurrentUser User user, @PathVariable("id") Long id, Model model) {
		OneBidItemDto oneBidItemDto = itemService.showOneBidItem(user, id);
		model.addAttribute("oneBidItemDto", oneBidItemDto);

		return "bid/bidPage";
	}

	@GetMapping("/wish-list")
	public String getWishListForm(@CurrentUser User user, Model model) {
		User userInfo = userService.getById(user.getId());
		model.addAttribute("user", userInfo);

		return "item/wishList";
	}

	@GetMapping("/bid-list")
	public String getBidListForm(@CurrentUser User user, Model model) {
		return "item/bidList";
	}
}
