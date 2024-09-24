package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.Customs;
import project.pickme.user.dto.LoginDto;

@RequestMapping("/customs")
@Controller
@RequiredArgsConstructor
public class CustomsController {
	@GetMapping("/loginForm")
	public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto){
		return "customs/loginForm";
	}

	@GetMapping("/incomeForm")
	public String incomeForm(@CurrentUser Customs customs, Model model){
		model.addAttribute("name", customs.getId());

		return "customs/incomeForm";
	}
}
