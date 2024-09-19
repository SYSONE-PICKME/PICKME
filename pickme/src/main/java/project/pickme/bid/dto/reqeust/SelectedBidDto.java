package project.pickme.bid.dto.reqeust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectedBidDto {
	private Long itemId;
	private Long bidId;
	private String itemName;
	private String itemImage;
}
