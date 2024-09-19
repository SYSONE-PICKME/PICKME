package project.pickme.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.service.FindItemService;
import project.pickme.user.domain.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainpageController {

	private final FindItemService itemService;

	@GetMapping
	public String showMain(@CurrentUser User user, Model model) {
		List<FindItemDto.GetAll> items = itemService.findAll(user.getId());
		model.addAttribute("items", items);
		return "index";
	}
}
