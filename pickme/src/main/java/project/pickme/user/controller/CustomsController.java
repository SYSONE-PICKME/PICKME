package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.user.dto.LoginDto;

@RequestMapping("/customs")
@Controller
@RequiredArgsConstructor
public class CustomsController {
	@GetMapping("/loginForm")
	public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto){
		return "customs/loginForm";
	}
}
