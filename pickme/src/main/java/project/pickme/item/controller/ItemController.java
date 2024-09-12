package project.pickme.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.item.ItemService;
import project.pickme.item.dto.ItemDto;

@Controller
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping("/{id}")
	public String getItemInfo(@PathVariable("id") Long id, Model model) {
		ItemDto.Info item = itemService.findById(id);
		model.addAttribute("item", item);

		return "item/item.html";
	}
}
