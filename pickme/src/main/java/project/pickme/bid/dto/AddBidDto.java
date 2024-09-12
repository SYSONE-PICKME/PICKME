package project.pickme.bid.dto;

import lombok.Getter;

@Getter
public class AddBidDto {
	private Long itemId;
	private String userId;
	private long price;
}
