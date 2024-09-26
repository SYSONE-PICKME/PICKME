package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryBidPriceRangeDto {
	private String categoryName;
	private Long minBidPrice;
	private Long maxBidPrice;
}
