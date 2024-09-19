package project.pickme.bid.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePriceBidDto extends PriceDto{
	private final String type = "priceUpdate";

	private UpdatePriceBidDto(Long bidId, long price, String userId){
		super(bidId, price, userId);
	}

	public static UpdatePriceBidDto createOf(Long bidId, long price, String userId) {
		return new UpdatePriceBidDto(bidId, price, userId);
	}
}
