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
import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;
import project.pickme.util.S3FileUploadService;

@Controller
@RequestMapping("/customs/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final S3FileUploadService s3FileUploadService;

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
		model.addAttribute("isCampaign", false);
		model.addAttribute("notice", new NoticeDto());
		return "notice/noticeForm";
	}

	@PostMapping("/create")
	public String createNoticeOrCampaign(@ModelAttribute CampaignDto campaignDto,
		@RequestParam("type") String type) throws IOException {
		if ("notice".equals(type)) {
			Long noticeId = noticeService.createNotice(campaignDto);
			return "redirect:/customs/notice/noticeContent/" + noticeId;
		} else if ("campaign".equals(type)) {
			if (campaignDto.getImageFile() != null && !campaignDto.getImageFile().isEmpty()) {
				String imageUrl = s3FileUploadService.uploadFile(campaignDto.getImageFile());
				campaignDto.setImageUrl(imageUrl);
			}
			CampaignDto savedCampaign = noticeService.createCampaign(campaignDto);
			return "redirect:/customs/notice/noticeContent/" + savedCampaign.getId();
		}
		return "redirect:/customs/notice";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model, @CurrentUser Customs customs) {
		NoticeDto notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		model.addAttribute("customsName", customs.getName());
		model.addAttribute("customsId", customs.getId());
		model.addAttribute("isEditing", true);
		model.addAttribute("isCampaign", notice instanceof CampaignDto);	// 이 부분은 security 관련된 부분이 계속 에러가 발생해 AI도움을 많이 받음
		return "notice/noticeForm";
	}

	@PostMapping("/edit/{id}")
	public String updateNoticeOrCampaign(@PathVariable("id") Long id,
		@ModelAttribute CampaignDto campaignDto,
		@RequestParam("type") String type) throws IOException {
		campaignDto.setId(id);
		if ("notice".equals(type)) {
			noticeService.updateNotice(campaignDto);
		} else if ("campaign".equals(type)) {
			if (campaignDto.getImageFile() != null && !campaignDto.getImageFile().isEmpty()) {
				String imageUrl = s3FileUploadService.uploadFile(campaignDto.getImageFile());
				campaignDto.setImageUrl(imageUrl);
			}
			noticeService.updateCampaign(campaignDto);
		}
		return "redirect:/customs/notice/noticeContent/" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteNotice(@PathVariable("id") Long id) {
		noticeService.deleteNotice(id);
		return "redirect:/customs/notice";
	}

	@GetMapping("/noticeContent/{id}")
	public String showNoticeOrCampaign(@PathVariable Long id, Model model) {
		NoticeDto notice = noticeService.getNoticeById(id);
		if (notice == null) {
			return "redirect:/customs/notice";
		}
		model.addAttribute("notice", notice);
		return "notice/noticeContent";
	}
}
