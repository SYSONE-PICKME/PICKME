package project.pickme.delivery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.response.BaseResponse;
import project.pickme.delivery.dto.DeliveryFormDto;
import project.pickme.delivery.service.DeliveryService;

@RequiredArgsConstructor
@RestController
public class DeliveryRestController {
	private final DeliveryService deliveryService;

	/**
	 * 인증 헤더를 반환합니다.
	 *
	 * @return 인증 헤더를 담은 Map을 반환하는 ResponseEntity
	 */
	@GetMapping("/tracking/getAuthHeader")
	public ResponseEntity<Map<String, String>> getAuthHeader() {
		Map<String, String> response = new HashMap<>();
		response.put("authHeader", deliveryService.getAuthHeader());
		return ResponseEntity.ok(response);
	}

	/**
	 * 송장 번호를 저장합니다.
	 *
	 * @param deliveryFormDto 송장 정보가 담긴 DTO
	 * @return 송장이 성공적으로 등록되었음을 알리는 메시지를 담은 ResponseEntity
	 */
	@PostMapping("/customs/delivery")
	public BaseResponse<String> saveInvoiceNumber(@RequestBody DeliveryFormDto deliveryFormDto) {
		deliveryService.saveDelivery(deliveryFormDto);
		return BaseResponse.ok("송장이 성공적으로 등록되었습니다."); // 성공 메시지 반환
	}
}
