package project.pickme.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.User;
import project.pickme.user.dto.LoginDto;
import project.pickme.user.dto.SignUpDto;
import project.pickme.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/signUpForm")
	public String userSignUpForm(@ModelAttribute("signUpDto") SignUpDto signUpDto) {
		return "user/signUpForm";
	}

	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute SignUpDto signUpDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("type", signUpDto.getType());
			return "user/signUpForm";
		}

		userService.signUp(signUpDto);
		return "redirect:/user/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto) {
		return "user/loginForm";
	}

	@GetMapping("/myPage")
	public String myPage(@CurrentUser User user, Model model) {
		model.addAttribute("user", user);

		return "user/userInfoEditForm";
	}

	@GetMapping("/point/historyForm")
	public String showPointHistory(@CurrentUser User user, Model model) {
		model.addAttribute("user", user);

		return "user/pointHistory";
	}
}
