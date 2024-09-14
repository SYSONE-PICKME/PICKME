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

	public static MaxPriceDto createOf(Long bidId, long maxPrice) {
		return new MaxPriceDto(bidId, maxPrice);
	}
}
