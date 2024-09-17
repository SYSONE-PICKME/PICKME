package project.pickme.notice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;

@Controller
@RequestMapping("/customs/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping
	public String listNotices(Model model) {
		List<NoticeDto> notices = noticeService.getAllNotices();
		model.addAttribute("notices", notices);
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
	public String createNotice(@ModelAttribute NoticeDto noticeDto) {
		Long noticeId = noticeService.createNotice(noticeDto);
		return "redirect:/customs/notices/" + noticeId;
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
	public String updateNotice(@PathVariable("id") Long id, @ModelAttribute NoticeDto noticeDto) {
		noticeDto.setId(id);
		noticeService.updateNotice(noticeDto);
		return "redirect:/customs/notices/" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteNotice(@PathVariable("id") Long id) {
		try {
			noticeService.deleteNotice(id);
			return "redirect:/customs/notices";
		} catch (Exception e) {
			return "redirect:/customs/notices?error=deletefailed";		// todo: 마찬가지로 AJAX로 변경할 때 처리
		}
	}

	@GetMapping("/{id}")
	public String showNotice(@PathVariable Long id, Model model) {
		NoticeDto notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		return "notice/noticeContent";
	}
}
