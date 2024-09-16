package project.pickme.payment.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SavePaymentDto {
	private String userId;
	private Long bidId;
	private LocalDateTime paymentTime;

	public static SavePaymentDto createOf(String userId, Long bidId){
		return new SavePaymentDto(userId, bidId, LocalDateTime.now());
	}
}
