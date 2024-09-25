package project.pickme.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/notices")
@RequiredArgsConstructor
public class UserNoticeController {

	@GetMapping
	public String showNotices() {
		return "notice/userNoticeList";
	}
}
