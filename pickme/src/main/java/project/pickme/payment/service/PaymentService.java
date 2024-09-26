package project.pickme.payment.service;

import static project.pickme.payment.exception.PaymentErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.payment.dto.PaymentDto;
import project.pickme.payment.repository.PaymentMapper;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentMapper paymentMapper;
	private final UserMapper userMapper;

	@Transactional
	public void payment(User user, PaymentDto paymentDto) {
		if(user.getPoint() < paymentDto.getPrice()){
			throw new BusinessException(INSUFFICIENT_PRICE);
		}

		userMapper.minusPoint(user.getId(), paymentDto.getPrice());	//포인트 차감
		paymentMapper.save(paymentDto.getBidId(), user.getId());	//payment 테이블 추가
	}
}
