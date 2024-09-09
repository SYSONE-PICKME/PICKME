package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.Customs;
import project.pickme.user.dto.LoginDto;
import project.pickme.user.service.CustomsService;

@RequestMapping("/customs")
@Controller
@RequiredArgsConstructor
public class CustomsController {
	private final CustomsService customsService;

	@GetMapping("/loginForm")
	public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto){
		return "customs/loginForm";
	}

	@GetMapping()
	public String test(@CurrentUser Customs customs){
		System.out.println(customs.toString());
		return "home";
	}
}
