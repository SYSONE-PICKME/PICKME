package project.pickme.bid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import project.pickme.common.annotation.CurrentUser;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user")
public class BidController {

	@GetMapping("/bid/{itemId}")
	public String show(@CurrentUser User user, @PathVariable("itemId") long itemId, Model model){
		//TODO: itemId에 해당하는 공매품 정보 넘겨주기
		model.addAttribute("itemId", itemId);
		model.addAttribute("userId", user.getId());

		return "bid/bidPage";	//이 페이지에 들어가는 순간 웹소켓 세션 연결
	}
}
