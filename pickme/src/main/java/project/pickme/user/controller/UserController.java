package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.user.dto.UserSignUpDto;
import project.pickme.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/signUpForm")
	public String userSignUpForm(){
		return "user/signUpForm";
	}

	@PostMapping("/signUp")
	public String userSignUP(@ModelAttribute UserSignUpDto userSignUpDto){
		userService.userSignUp(userSignUpDto);

		return "redirect:/user/signUpForm";	//TODO: 로그인 화면으로 변경하기
	}
}
