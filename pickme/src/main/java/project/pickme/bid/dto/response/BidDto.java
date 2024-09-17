package project.pickme.bid.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidDto extends PriceDto {
	private LocalDateTime bidTime;
	private boolean isSuccess;
	private Long itemId;

	private BidDto(Long bidId, long price, String userId, LocalDateTime bidTime, boolean isSuccess, Long itemId) {
		super(bidId, price, userId);
		this.bidTime = bidTime;
		this.isSuccess = isSuccess;
		this.itemId = itemId;
	}

	public static BidDto create(long price, String userId, Long itemId) {
		return new BidDto(null, price, userId, LocalDateTime.now(), false, itemId);
	}
}
