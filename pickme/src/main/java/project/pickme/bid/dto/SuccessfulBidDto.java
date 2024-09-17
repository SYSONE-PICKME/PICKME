package project.pickme.bid.dto;

import lombok.Getter;

@Getter
public class SuccessfulBidDto {
	private String itemNum;
	private String userId;
	private String itemName;
	private long price;
	private String imgUrl;
	private String customsName;
}
