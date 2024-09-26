package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyBidActivityDto {
	private int hourOfDay;
	private int bidCount;
}
