package project.pickme.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;

@Controller
@RequestMapping("/user/notices")
@RequiredArgsConstructor
public class UserNoticeController {

	private final NoticeService noticeService;

	@GetMapping
	public String showNotices() {
		return "notice/userNoticeList";
	}

	@GetMapping("/{id}")
	public String showNoticeContent(@PathVariable Long id, Model model) {
		NoticeDto notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		return "notice/userNoticeContent";
	}
}
