package project.pickme.charge.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;
import project.pickme.charge.dto.ChargeRequest;
import project.pickme.charge.service.ChargeService;
import project.pickme.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/charge")
public class ChargeRestController {
	private final ChargeService chargeService;

	/**
	 * 결제 완료 요청을 처리하는 메서드입니다.
	 *
	 * 이 메서드는 클라이언트로부터 결제 요청 정보를 받아,
	 * 결제 서비스를 호출하여 결제를 완료하고 그 결과를 반환합니다.
	 *
	 * @param request 결제 요청 정보를 담고 있는 {@link ChargeRequest} 객체
	 * @return 결제가 성공적으로 처리된 경우 "결제가 성공적으로 완료되었습니다."라는 메시지를 담은 {@link BaseResponse} 객체를 반환
	 * @throws IamportResponseException 결제 처리 중 아임포트 API 응답에서 오류가 발생한 경우
	 * @throws IOException 결제 처리 중 I/O 관련 오류가 발생한 경우
	 */
	@PostMapping("/complete")
	public BaseResponse<?> complete(@RequestBody ChargeRequest request) throws IamportResponseException, IOException {
		chargeService.charge(request);

		return BaseResponse.ok("결제가 성공적으로 완료되었습니다.");
	}
}
