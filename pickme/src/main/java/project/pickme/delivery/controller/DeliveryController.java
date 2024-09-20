package project.pickme.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.delivery.dto.DeliveryFormDto;
import project.pickme.delivery.service.DeliveryService;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customs/delivery")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@GetMapping
	public String showDeliveryAdminPage(Model model, @CurrentUser Customs customs) {
		model.addAttribute("items", deliveryService.getCustomsSuccessfulItems(customs));

		return "/delivery/delivery";
	}

	@GetMapping("/register")
	public String showRegisterPage(@RequestParam("itemId") long itemId, @RequestParam("userId") String userId,
		Model model) {
		model.addAttribute("itemId", itemId);
		model.addAttribute("userId", userId);
		model.addAttribute("deliveryInfo", new DeliveryFormDto());// 폼을 위한 객체

		return "/delivery/register";
	}

	// TODO: 송장 등록 하는거 다시 해야함.
	@PostMapping
	public String saveInvoiceNumber(@RequestBody DeliveryFormDto deliveryFormDto) {
		deliveryService.saveDelivery(deliveryFormDto);
		return "redirect:/customs/delivery";
	}

	// TODO: 배송 상황 아직 미구현
	@GetMapping("/status")
	public String showStatusPage(@RequestParam("itemId") Long itemId, Model model) {
		model.addAttribute("itemId", itemId);

		return "/delivery/status";
	}
}