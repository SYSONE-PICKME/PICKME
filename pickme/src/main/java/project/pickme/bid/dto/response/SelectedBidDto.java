package project.pickme.bid.dto.response;

import lombok.Getter;

@Getter
public class SelectedBidDto {
	private String type;
	private Long itemId;
	private Long bidId;
	private long price;
	private String itemName;
	private String itemImage;
}
