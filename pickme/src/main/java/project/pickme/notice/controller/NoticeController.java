package project.pickme.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import project.pickme.common.auth.CustomsDetailsImpl;
import project.pickme.notice.dto.CampaignDTO;
import project.pickme.notice.dto.NoticeDTO;
import project.pickme.notice.service.NoticeService;
import project.pickme.user.domain.Customs;
import project.pickme.util.FileUploadService;

@Controller
@RequestMapping("/customs/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping
	private String listNotice(Model model) {
		List<NoticeDTO> listNotice = noticeService.getAllNotice();
		model.addAttribute("notices", listNotice);
		return "notice/noticeList";
	}

	@GetMapping("/newNotice")
	public String showNoticeForm(Model model, Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof CustomsDetailsImpl) {
			CustomsDetailsImpl customsDetails = (CustomsDetailsImpl) authentication.getPrincipal();
			Customs customs = customsDetails.getCustoms();
			model.addAttribute("customsName", customs.getName());
			model.addAttribute("customsId", customs.getId());
		} else {
			model.addAttribute("customsName", "Unknown");
			model.addAttribute("customsId", "");
		}
		model.addAttribute("notice", new NoticeDTO());
		return "notice/noticeForm";
	}

	@PostMapping
	public String createNotice(@ModelAttribute NoticeDTO noticeDTO, Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof CustomsDetailsImpl) {
			CustomsDetailsImpl customsDetails = (CustomsDetailsImpl) authentication.getPrincipal();
			Customs customs = customsDetails.getCustoms();
			noticeDTO.setCustomsId(customs.getId());
		} else {
			return "redirect:/login";
		}

		NoticeDTO savedNotice = noticeService.createNotice(noticeDTO);
		return "redirect:/customs/notice/noticeContent/" + savedNotice.getId();
	}

	@GetMapping("/editNotice/{id}")
	public String updateNotice(@PathVariable("id") long id, Model model, Authentication authentication) {
		NoticeDTO notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);

		if (authentication != null && authentication.getPrincipal() instanceof CustomsDetailsImpl) {
			CustomsDetailsImpl customsDetails = (CustomsDetailsImpl) authentication.getPrincipal();
			Customs customs = customsDetails.getCustoms();
			model.addAttribute("customsName", customs.getName());
			model.addAttribute("customsId", customs.getId());
		} else {
			model.addAttribute("customsName", "Unknown");
			model.addAttribute("customsId", "");
		}

		return "notice/noticeForm";
	}

	@PostMapping("/edit/{id}")
	public String updateNotice(@PathVariable("id") long id, @ModelAttribute NoticeDTO noticeDTO) {
		noticeDTO.setId(id);
		noticeService.updateNotice(noticeDTO);
		return "redirect:/customs/notice/noticeContent/" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteNotice(@PathVariable("id") long id) {
		noticeService.deleteNotice(id);
		return "redirect:/customs/notice";
	}

	@GetMapping("/newCampaign")
	public String showCampaignForm(Model model, Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof CustomsDetailsImpl) {
			CustomsDetailsImpl customsDetails = (CustomsDetailsImpl) authentication.getPrincipal();
			Customs customs = customsDetails.getCustoms();
			model.addAttribute("customsName", customs.getName());
			model.addAttribute("customsId", customs.getId());
		} else {
			return "redirect:/login";
		}
		model.addAttribute("campaign", new CampaignDTO());
		return "notice/campaignForm";
	}

	@PostMapping("/campaign")
	public String createCampaign(@RequestParam("title") String title,
		@RequestParam("imageFile") MultipartFile imageFile,
		Authentication authentication) throws IOException {
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setTitle(title);
		campaignDTO.setContent("");

		if (!imageFile.isEmpty()) {
			String imageUrl = fileUploadService.uploadFile(imageFile);
			campaignDTO.setImageUrl(imageUrl);
		}

		if (authentication != null && authentication.getPrincipal() instanceof CustomsDetailsImpl) {
			CustomsDetailsImpl customsDetails = (CustomsDetailsImpl) authentication.getPrincipal();
			campaignDTO.setCustomsId(customsDetails.getCustoms().getId());
		}

		CampaignDTO savedCampaign = noticeService.createCampaign(campaignDTO);
		return "redirect:/customs/notice/noticeContent/" + savedCampaign.getId();
	}

	@GetMapping("/noticeContent/{id}")
	public String showNoticeOrCampaign(@PathVariable Long id, Model model) {
		NoticeDTO notice = noticeService.getNoticeById(id);
		if (notice == null) {
			return "redirect:/customs/notice";
		}
		if (notice.getContent().contains("[Image URL: ")) {
			notice = noticeService.getCampaignById(id);
		}
		model.addAttribute("notice", notice);
		return "notice/noticeContent";
	}
}
