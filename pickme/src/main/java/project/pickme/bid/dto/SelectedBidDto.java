package project.pickme.bid.dto;

import lombok.Getter;

@Getter
public class SelectedBidDto {
	private String type;
	private Long itemId;
	private Long bidId;
	private long selectedPrice;
}
