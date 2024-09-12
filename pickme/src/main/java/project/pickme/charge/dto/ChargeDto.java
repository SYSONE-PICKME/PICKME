package project.pickme.charge.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChargeDto {

	@Getter
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Create {
		private final long price;
		private final LocalDateTime chargeTime;
		private final String userId;

		public static Create of(long price, String userId) {
			return new Create(price, LocalDateTime.now(), userId);
		}
	}
}
