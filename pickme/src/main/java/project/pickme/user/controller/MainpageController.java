package project.pickme.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.item.FindItemService;
import project.pickme.item.dto.FindItemDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainpageController {

	private final FindItemService itemService;

	@GetMapping
	public String showMain(Model model) {
		List<FindItemDto.GetAll> items = itemService.findAll();
		model.addAttribute("items", items);
		return "index";
	}
}
