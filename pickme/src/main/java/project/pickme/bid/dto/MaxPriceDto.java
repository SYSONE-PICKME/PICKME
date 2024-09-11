package project.pickme.bid.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaxPriceDto {
	private Long bidId;
	private long maxPrice;

	public static MaxPriceDto create(Long maxPriceBidId, long maxPrice) {
		return new MaxPriceDto(maxPriceBidId, maxPrice);
	}
}
