package project.pickme.bid.dto.reqeust;

import lombok.Getter;

@Getter
public class AddBidDto {
	private String type;
	private Long itemId;
	private String userId;
	private long price;
}
