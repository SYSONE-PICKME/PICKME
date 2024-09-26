package project.pickme.bid.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SelectedMyBidDto {
	private String itemName;
	private String itemImage;
	private String selectedPrice;
	private LocalDateTime selectedTime;
}
