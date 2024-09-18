package project.pickme.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.delivery.service.DeliveryService;
import project.pickme.user.domain.Customs;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customs/delivery")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@GetMapping("/register")
	public String showRegisterPage(@RequestParam("itemId") Long itemId, Model model) {
		model.addAttribute("itemId", itemId);

		return "/delivery/register";
	}

	@GetMapping("/status")
	public String showStatusPage(@RequestParam("itemId") Long itemId, Model model) {
		model.addAttribute("itemId", itemId);

		return "/delivery/status";
	}

	@GetMapping("/admin")
	public String showDeliveryAdminPage(Model model, @CurrentUser Customs customs) {
		model.addAttribute("items", deliveryService.getCustomsSuccessfulItems(customs));
		return "/delivery/deliveryAdmin";
	}
}
