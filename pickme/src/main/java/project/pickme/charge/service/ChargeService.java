package project.pickme.charge.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import project.pickme.charge.dto.ChargeDto;
import project.pickme.charge.dto.ChargeRequest;
import project.pickme.charge.mapper.ChargeMapper;
import project.pickme.user.domain.User;
import project.pickme.user.dto.UserDto;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
public class ChargeService {
	private final IamportClient iamportClient;
	private final ChargeMapper chargeMapper;
	private final UserMapper userMapper;

	@Transactional
	public void charge(ChargeRequest request) throws IamportResponseException, IOException {
		IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(request.getImpUid());
		Payment payment = paymentResponse.getResponse();

		if (isNotEqualsAmount(payment.getAmount(), request.getPoint())) {
			throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
		}

		updateUserPoint(request.getUserId(), request.getPoint());
	}

	private boolean isNotEqualsAmount(BigDecimal paymentAmount, long amount) {
		return !paymentAmount.equals(new BigDecimal(amount));
	}

	private void updateUserPoint(final String userId, final long point) {
		final long updatedPoint = getUser(userId).getPoint() + point;

		userMapper.updatePointById(new UserDto.UpdatePoint(userId, updatedPoint));
		chargeMapper.save(ChargeDto.Create.of(point, userId));
	}

	public UserDto.Info getUserInfo(String userId) {
		User user = getUser(userId);
		return new UserDto.Info(userId, user.getName(), user.getPoint());
	}

	private User getUser(String id) {
		return userMapper.findUserById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
	}
}
