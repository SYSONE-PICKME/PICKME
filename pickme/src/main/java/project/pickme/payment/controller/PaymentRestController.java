package project.pickme.payment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.payment.dto.PaymentDto;
import project.pickme.payment.service.PaymentService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class PaymentRestController {
	private final PaymentService paymentService;

	@PostMapping("/payment")
	public BaseResponse<?> payment(@RequestBody PaymentDto paymentDto, @CurrentUser User user) {
		paymentService.payment(user, paymentDto);
		return BaseResponse.ok();
	}
}
