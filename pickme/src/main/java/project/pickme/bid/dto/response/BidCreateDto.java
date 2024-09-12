package project.pickme.bid.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class BidCreateDto {
	private Long bidId;    //save 후 반환 받을 저장된 BidId
	private long price;
	private LocalDateTime bidTime;
	private boolean isSuccess;
	private String userId;
	private Long itemId;

	public static BidCreateDto create(long price, String userId, Long itemId) {
		return BidCreateDto.builder()
			.price(price)
			.bidTime(LocalDateTime.now())
			.isSuccess(false)
			.userId(userId)
			.itemId(itemId)
			.build();
	}
}
