package project.pickme.delivery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.delivery.dto.DeliveryFormDto;
import project.pickme.delivery.service.DeliveryService;

@RequiredArgsConstructor
@RestController
public class DeliveryRestController {
	private final DeliveryService deliveryService;

	@GetMapping("/tracking/getAuthHeader")
	public ResponseEntity<Map<String, String>> getAuthHeader() {
		Map<String, String> response = new HashMap<>();
		response.put("authHeader", deliveryService.getAuthHeader());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/customs/delivery")
	public ResponseEntity<String> saveInvoiceNumber(@RequestBody DeliveryFormDto deliveryFormDto) {
		deliveryService.saveDelivery(deliveryFormDto);
		return ResponseEntity.ok("송장이 성공적으로 등록되었습니다."); // 성공 메시지 반환
	}
}
