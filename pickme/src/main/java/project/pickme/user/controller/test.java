package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {
	@GetMapping("/user/home")
	public String userHome(){
		return "home";
	}

	@GetMapping("/customs/home")
	public String home(){
		return "home";
	}
}
