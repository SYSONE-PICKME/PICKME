package project.pickme.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.pickme.notice.domain.Notice;
import project.pickme.notice.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@GetMapping
	public String listNotice(Model model){
		List<Notice> notices = noticeService.getAllNotice();
		model.addAttribute("notices", notices);
		return "notice/noticeList";
	}
}
