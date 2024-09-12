package project.pickme.bid.dto;

import lombok.Getter;

@Getter
public class AddBidDto {
	private String type;
	private Long itemId;
	private String userId;
	private long price;

	@Override
	public String toString() {
		return "AddBidDto{" +
			"type='" + type + '\'' +
			", itemId=" + itemId +
			", userId='" + userId + '\'' +
			", price=" + price +
			'}';
	}
}
