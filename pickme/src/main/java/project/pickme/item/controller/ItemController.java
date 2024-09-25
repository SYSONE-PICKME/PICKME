package project.pickme.item.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.constant.Category;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.UpdateItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.constant.Type;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customs/items")
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/new")
	public String itemForm(Model model) {
		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("types", Type.values());
		model.addAttribute("categories", Category.values());

		return "/item/createItem"; // 같은 뷰 리턴
	}

	@PostMapping
	public String insertItem(@ModelAttribute ItemFormDto itemFormDto, @CurrentUser Customs customs) throws IOException {
		itemService.save(itemFormDto, customs);

		return "redirect:/customs/items";
	}

	@GetMapping
	public String getItems(@CurrentUser Customs customs, Model model) {
		model.addAttribute("items", itemService.findItemsByCustomsId(customs.getId()));

		return "/item/customsItems";
	}

	@GetMapping("/edit/{id}")
	public String editItemForm(@PathVariable long id, Model model) {
		model.addAttribute("selectedItem", itemService.getItemById(id));
		model.addAttribute("selectedLaws", itemService.getLawsByItemId(id));
		model.addAttribute("selectedImages", itemService.getImagesByItemId(id));
		model.addAttribute("form", new UpdateItemFormDto());
		model.addAttribute("id", id);

		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("types", Type.values());
		model.addAttribute("categories", Category.values());
		return "/item/editItem";
	}
}
