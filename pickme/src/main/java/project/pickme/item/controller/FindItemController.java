package project.pickme.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.service.FindItemService;
import project.pickme.item.dto.FindItemDto;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class FindItemController {
	private final FindItemService itemService;

	@GetMapping("/{id}")
	public String getItemInfo(@CurrentUser User user, @PathVariable("id") Long id, Model model) {
		FindItemDto.Info item = itemService.findById(id, user.getId());
		model.addAttribute("item", item);

		return "item/item.html";
	}
}
