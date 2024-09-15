package project.pickme.item.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.constant.Category;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.constant.Type;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customs/item")
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/create")
	public String getItem(Model model) {
		model.addAttribute("item", new ItemFormDto());
		model.addAttribute("laws", itemService.findAllLaws());
		model.addAttribute("type", Type.values());
		model.addAttribute("categories", Category.values());

		return "item/create-item";
	}

	@PostMapping("/create")
	public String insertItem(@ModelAttribute ItemFormDto itemFormDto,
		@RequestPart(value = "files", required = false) MultipartFile[] files, @CurrentUser
	Customs customs) throws IOException {
		itemService.save(itemFormDto, files, customs);

		return "redirect:/customs/item/create";
	}
}
