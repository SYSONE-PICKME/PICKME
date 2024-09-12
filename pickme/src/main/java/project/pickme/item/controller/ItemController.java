package project.pickme.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.item.domain.Category;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.constant.Type;

@Controller
@RequestMapping(value = "/customs/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	@GetMapping("/create")
	public String getItem(Model model) {
		@ModelAttribute ItemFormDto itemFormDto;
		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("type", Type.values());
		model.addAttribute("categories", Category.values());
		return "create-item";
	}

	@PostMapping("/create")
	public String insertItem(@ModelAttribute ItemFormDto itemFormDto) {
		itemService.save(itemFormDto);
		return "redirect:/customs/item/create";
	}
}
