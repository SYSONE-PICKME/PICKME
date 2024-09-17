package project.pickme.bid.dto.reqeust;

import lombok.Getter;

@Getter
public class SelectedBidDto {
	private Long itemId;
	private Long bidId;
	private String itemName;
	private String itemImage;
}
