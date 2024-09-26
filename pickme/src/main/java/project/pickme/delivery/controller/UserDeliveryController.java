package project.pickme.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.delivery.service.DeliveryService;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user/delivery")
@RequiredArgsConstructor
public class UserDeliveryController {

	private final DeliveryService deliveryService;

	@GetMapping("/status")
	public String showStatusPage(@RequestParam("itemId") long itemId, @RequestParam("userId") String userId,
		Model model, @CurrentUser User user) {
		model.addAttribute("user", user);
		model.addAttribute("item", deliveryService.getDeliveryItemInfo(itemId));
		model.addAttribute("itemUser", deliveryService.getDeliveryInfo(userId));
		model.addAttribute("itemId", itemId);

		return "/delivery/userStatus";
	}
}
