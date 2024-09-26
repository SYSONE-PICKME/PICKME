package project.pickme.bid.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class UnPaidBidDto {
	private String bidId;
	private String itemName;
	private String itemImage;
	private String price;
	private LocalDateTime selectedTime;
}
