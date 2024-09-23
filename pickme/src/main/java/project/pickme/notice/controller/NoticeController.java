package project.pickme.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;

@Controller
@RequestMapping("/customs/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping
	public String showNotices(Model model, @CurrentUser Customs customs) {
		List<NoticeDto> notices = noticeService.getAllNotices();
		model.addAttribute("notices", notices);
		model.addAttribute("customsId", customs.getId());
		return "notice/noticeList";
	}

	@GetMapping("/new")
	public String showNewNoticeForm(Model model, @CurrentUser Customs customs) {
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("isEditing", false);
		model.addAttribute("notice", new NoticeDto());
		return "notice/noticeForm";
	}

	@PostMapping("/create")
	public String createNotice(@ModelAttribute NoticeDto noticeDto, @CurrentUser Customs customs) {
		Long id = noticeService.createNotice(noticeDto, customs);
		return "redirect:/customs/notices/" + id;
	}

	@GetMapping("/edit/{id}")
	public String showEditNoticeForm(@PathVariable("id") Long id, Model model, @CurrentUser Customs customs) {
		NoticeDto notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("isEditing", true);
		return "notice/noticeForm";
	}

	@PostMapping("/edit/{id}")
	public String updateNotice(@PathVariable("id") Long id, @ModelAttribute NoticeDto noticeDto,
		@CurrentUser Customs customs) {
		noticeService.updateNotice(noticeDto, customs);
		return "redirect:/customs/notices/" + id;
	}

	@GetMapping("/{id}")
	public String showNotice(@PathVariable Long id, Model model, @CurrentUser Customs customs) {
		NoticeDto notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		model.addAttribute("customsId", customs.getId());
		return "notice/noticeContent";
	}
}
