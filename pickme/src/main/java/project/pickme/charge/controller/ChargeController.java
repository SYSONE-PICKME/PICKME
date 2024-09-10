package project.pickme.charge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.pickme.user.constant.Role;
import project.pickme.user.constant.Type;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/charge")
public class ChargeController {

	@GetMapping
	public String charge(Model model) {
		User user = User.builder()
			.id("test")
			.password("1234")
			.role(Role.ROLE_USER)
			.name("테스트")
			.type(Type.USER)
			.addr("서울특별시 종로구")
			.email("test@naver.com")
			.point(50000)
			.build();

		model.addAttribute("user", user);
		return "charge/charge.html";
	}
}
