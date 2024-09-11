package project.pickme.charge.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;
import project.pickme.charge.dto.ChargeRequest;
import project.pickme.charge.service.ChargeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/charge")
public class ChargeRestController {
	private final ChargeService chargeService;

	@PostMapping("/complete")
	public ResponseEntity<?> complete(@RequestBody() ChargeRequest request) throws IamportResponseException, IOException {
		chargeService.charge(request);

		return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
	}
}
