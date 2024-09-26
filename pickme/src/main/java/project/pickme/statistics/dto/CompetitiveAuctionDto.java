package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitiveAuctionDto {
	private Long itemId;
	private String itemName;
	private int participantCount;
}
