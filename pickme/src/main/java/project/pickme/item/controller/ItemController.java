package project.pickme.item.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.constant.Category;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.constant.Type;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customs/items")
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/new")
	public String newItemForm(Model model) {
		model.addAttribute("item", new ItemFormDto());
		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("types", Type.values());
		model.addAttribute("categories", Category.values());

		return "/item/createItem";
	}

	@PostMapping
	public String insertItem(@ModelAttribute ItemFormDto itemFormDto, @CurrentUser
	Customs customs) throws IOException {
		itemService.save(itemFormDto, customs);

		return "redirect:/customs/items";
	}

	@GetMapping
	public String getItems(@CurrentUser Customs customs, Model model) {
		model.addAttribute("items", itemService.findItemsByCustomsId(customs.getId()));

		return "/item/customsItems";
	}

	@GetMapping("/update")
	public String showStatusPage(@RequestParam("itemId") Long itemId, Model model) {
		model.addAttribute("itemId", itemId);
		return "/item/updateItem";
	}
}
