package project.pickme.delivery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.delivery.service.DeliveryService;

@RequiredArgsConstructor
@RequestMapping("/tracking/")
@RestController
public class DeliveryRestController {
	private final DeliveryService deliveryService;

	@GetMapping("/getAuthHeader")
	public ResponseEntity<Map<String, String>> getAuthHeader() {
		try {
			Map<String, String> response = new HashMap<>();
			response.put("authHeader", deliveryService.getAuthHeader());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// 예외가 발생했을 때 HTTP 500 상태 코드와 오류 메시지를 반환
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Failed to retrieve auth header: " + e.getMessage()));
		}
	}
}
