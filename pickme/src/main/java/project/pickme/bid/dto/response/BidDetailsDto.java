package project.pickme.bid.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BidDetailsDto {
	private List<Long> allPrice;
	private long maxPrice;
	private String userId; //현재 최고가 유저
	private Long bidId;    //현재 최고 입찰 아이디
	private long myPoint;

	public static BidDetailsDto createOf(List<PriceDto> priceDtos, long point) {
		PriceDto last = priceDtos.getLast();
		List<Long> allPrice = priceDtos.stream().map(PriceDto::getPrice).toList();

		return new BidDetailsDto(allPrice, last.getPrice(), last.getUserId(), last.getBidId(), point);
	}
}
