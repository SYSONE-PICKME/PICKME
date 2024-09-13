package project.pickme.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;
import project.pickme.util.FileUploadService;

@Controller
@RequestMapping("/customs/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final FileUploadService fileUploadService;

	@GetMapping
	private String showNotice(Model model) {
		List<NoticeDto> listNotice = noticeService.getAllNotice();
		model.addAttribute("notices", listNotice);
		return "notice/noticeList";
	}

	@GetMapping("/newNotice")
	public String showNoticeForm(Model model, @CurrentUser Customs currentUser, @ModelAttribute("notice") NoticeDto noticeDto) {
		model.addAttribute("customsName", currentUser.getName());
		model.addAttribute("customsId", currentUser.getId());
		return "notice/noticeForm";
	}

	@PostMapping
	public String createNotice(@ModelAttribute NoticeDto noticeDto, @CurrentUser Customs customs){
		NoticeDto newNoticeDto = NoticeDto.builder()
			.title(noticeDto.getTitle())
			.content(noticeDto.getContent())
			.customsId(customs.getId())
			.build();
		NoticeDto savedNotice = noticeService.createNotice(newNoticeDto);
		return "redirect:/customs/notice/noticeContent/" + savedNotice.getId();
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") long id, Model model, @CurrentUser Customs customs) {
		NoticeDto notice = noticeService.getNoticeOrCampaignById(id);
		model.addAttribute("notice", notice);
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		return "notice/noticeForm";
	}

	@PostMapping("/edit/{id}")
	public String updateNotice(@PathVariable("id") long id, @ModelAttribute NoticeDto noticeDto) {
		NoticeDto updatedNoticeDto = NoticeDto.builder()
			.id(id)
			.title(noticeDto.getTitle())
			.content(noticeDto.getContent())
			.customsId(noticeDto.getCustomsId())
			.build();
		noticeService.updateNotice(updatedNoticeDto);
		return "redirect:/customs/notice/noticeContent/" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteNotice(@PathVariable("id") long id) {
		noticeService.deleteNotice(id);
		return "redirect:/customs/notice";
	}

	@GetMapping("/newCampaign")
	public String showCampaignForm(Model model, @CurrentUser Customs customs) {
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("campaign", new CampaignDto());
		return "notice/campaignForm";
	}

	@PostMapping("/campaign")
	public String createCampaign(@RequestParam("title") String title,
		@RequestParam("imageFile") MultipartFile imageFile,
		@CurrentUser Customs customs) throws IOException {
		String imageUrl = null;
		if (!imageFile.isEmpty()) {
			imageUrl = fileUploadService.uploadFile(imageFile);
		}

		CampaignDto campaignDto = CampaignDto.campaignBuilder()
			.title(title)
			.content("")
			.customsId(customs.getId())
			.imageUrl(imageUrl)
			.build();

		CampaignDto savedCampaign = noticeService.createCampaign(campaignDto);
		return "redirect:/customs/notice/noticeContent/" + savedCampaign.getId();
	}

	@GetMapping("/noticeContent/{id}")
	public String showNoticeOrCampaign(@PathVariable Long id, Model model) {
		NoticeDto notice = noticeService.getNoticeOrCampaignById(id);
		if (notice == null) {
			return "redirect:/customs/notice";
		}
		model.addAttribute("notice", notice);
		return "notice/noticeContent";
	}
}
