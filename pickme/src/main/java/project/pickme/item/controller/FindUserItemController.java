package project.pickme.item.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.service.FindItemService;
import project.pickme.item.dto.FindItemDto;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class FindUserItemController {
	private final FindItemService itemService;

	@GetMapping("/{id}")
	public String getItemInfo(@CurrentUser User user, @PathVariable("id") Long id, Model model) {
		FindItemDto.Info item = itemService.findById(id, user.getId());
		model.addAttribute("item", item);

		return "item/item";
	}

	@GetMapping("/list")
	public String getAllItems(
		@CurrentUser User user,
		@RequestParam(name = "category", required = false, defaultValue = "all") String category,
		Model model
	) {
		List<FindItemDto.GetAll> items = itemService.findAll(user.getId(), category);
		model.addAttribute("items", items);

		return "item/list";
	}

	@GetMapping("/bid/{id}")
	public String show(@CurrentUser User user, @PathVariable("id") Long id, Model model) {
		OneBidItemDto oneBidItemDto = itemService.showOneBidItem(user, id);
		model.addAttribute("oneBidItemDto", oneBidItemDto);

		return "bid/bidPage";
	}

	@GetMapping("/wish-list-form")
	public String getWishListForm(@CurrentUser User user, Model model) {
		model.addAttribute("user", user);

		return "item/wishList";
	}

	@GetMapping("/bid-list")
	public String getBidList(
		@CurrentUser User user,
		@RequestParam(name = "category", required = false, defaultValue = "all") String category,
		Model model
	) {
		List<FindItemDto.MyBid> bidList = itemService.findBidList(user.getId(), category);
		model.addAttribute("bidList", bidList);

		return "item/bidList";
	}
}
