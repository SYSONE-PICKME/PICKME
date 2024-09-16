package project.pickme.bid.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BidDetailsDto {
	private List<Long> allPrice;
	private Long maxPrice;
	private long myPoint;

	public static BidDetailsDto createOf(List<Long> allPrice, long point) {
		return new BidDetailsDto(allPrice, allPrice.getLast(), point);
	}
}
