package project.pickme.item.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping({"/new", "/edit/{id}"})
	public String itemForm(@PathVariable(required = false) Long id, Model model) {
		if (id != null) {
			// 수정할 경우
			// ItemFormDto item = itemService.findItemById(id);
			// model.addAttribute("item", item);
		} else {
			// 새로 등록할 경우
			model.addAttribute("item", new ItemFormDto());
		}
		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("types", Type.values());
		model.addAttribute("categories", Category.values());

		return "/item/createItem"; // 같은 뷰 리턴
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
