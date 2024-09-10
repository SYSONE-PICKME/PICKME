package project.pickme.charge.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import project.pickme.charge.dto.ChargeDto;
import project.pickme.charge.mapper.ChargeMapper;

@Service
@RequiredArgsConstructor
public class ChargeService {
	private final IamportClient iamportClient;
	private final ChargeMapper chargeMapper;

	@Transactional
	public void charge(String impUid, int amount) throws IamportResponseException, IOException {
		IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
		Payment payment = paymentResponse.getResponse();

		if (isNotEqualsAmount(payment.getAmount(), amount)) {
			throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
		}

		// todo: 사용자 point update
		save(ChargeDto.Create.of(amount, "test"));
	}

	private boolean isNotEqualsAmount(BigDecimal paymentAmount, int amount) {
		return !paymentAmount.equals(new BigDecimal(amount));
	}

	public void save(ChargeDto.Create charge) {
		chargeMapper.save(charge);
	}
}
