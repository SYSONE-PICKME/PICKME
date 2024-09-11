package project.pickme.item;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.item.constant.Status;
import project.pickme.item.domain.Category;
import project.pickme.item.domain.ItemDTO;
import project.pickme.item.domain.ItemFormDTO;
import project.pickme.item.domain.LawDTO;
import project.pickme.item.service.ItemService;
import project.pickme.item.service.LawService;
import project.pickme.item.service.S3Service;
import project.pickme.user.constant.Type;

@Controller
@RequestMapping(value = "/customs/item")
@RequiredArgsConstructor
public class ItemController {

	private final LawService lawService;

	private final ItemService itemService;

	private final S3Service s3Service;

	@GetMapping("/create")
	public String getItem(Model model) {
		List<LawDTO> laws = lawService.getLaws();
		model.addAttribute("item", new ItemFormDTO());
		model.addAttribute("laws", laws);
		model.addAttribute("type", Type.values());
		model.addAttribute("categories", Category.values());
		return "create-item";
	}

	@PostMapping("/create")
	public String insertItem(@ModelAttribute ItemFormDTO itemFormDTO) {
		LocalDateTime start = LocalDateTime.of(itemFormDTO.getStartDate(), itemFormDTO.getStartTime());
		LocalDateTime end = LocalDateTime.of(itemFormDTO.getEndDate(), itemFormDTO.getEndTime());
		ItemDTO itemDTO = new ItemDTO(itemFormDTO.getName(), itemFormDTO.getCode(), itemFormDTO.getTarget(),
			itemFormDTO.getPrice(), start, end,
			Status.NOT_OPEN, "gunsan");

		itemService.save(itemDTO);
		return "redirect:/customs/item/create";
	}

}
