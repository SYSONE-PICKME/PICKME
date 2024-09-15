package project.pickme.bid.dto;

import lombok.Getter;

@Getter
public class SuccessfulBidDto {
	private String imgUrl;
	private String itemNum;
	private String itemName;
	private String customsName;
	private long price;
}
