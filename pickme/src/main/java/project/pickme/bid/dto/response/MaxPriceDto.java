package project.pickme.bid.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaxPriceDto {
	private final String type = "priceUpdate";
	private Long bidId;
	private long maxPrice;
	private String userId;

	public static MaxPriceDto createOf(Long bidId, long maxPrice, String userId) {
		return new MaxPriceDto(bidId, maxPrice, userId);
	}
}
