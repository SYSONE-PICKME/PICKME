package project.pickme.charge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.charge.service.ChargeService;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.User;
import project.pickme.user.dto.UserDto;

@Controller
@RequestMapping("/user/charge")
@RequiredArgsConstructor
public class ChargeController {
	private final ChargeService chargeService;

	@GetMapping
	public String charge(@CurrentUser User user, Model model) {
		UserDto.Info userInfo = chargeService.getUserInfo(user.getId());
		model.addAttribute("user", userInfo);

		return "charge/charge.html";
	}
}
