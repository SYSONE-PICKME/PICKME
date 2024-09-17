package project.pickme.bid.dto.reqeust;

import lombok.Getter;

@Getter
public class SelectedBidDto {
	private String type;
	private Long itemId;
	private Long bidId;
	private String userId;    //낙찰된 사람 아이디
	private String itemName;
	private String itemImage;
}
