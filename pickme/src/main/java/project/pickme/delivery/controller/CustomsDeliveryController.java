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
public class CustomsDeliveryController {

	private final DeliveryService deliveryService;

	@GetMapping
	public String showDeliveryAdminPage(Model model, @CurrentUser Customs customs) {
		model.addAttribute("items", deliveryService.getCustomsSuccessfulItems(customs));

		return "delivery/delivery";
	}

	@GetMapping("/status")
	public String showStatusPage(@RequestParam("itemId") long itemId, @RequestParam("userId") String userId,
		Model model) {
		model.addAttribute("item", deliveryService.getDeliveryItemInfo(itemId));
		model.addAttribute("user", deliveryService.getDeliveryInfo(userId));

		return "delivery/status";
	}
}
