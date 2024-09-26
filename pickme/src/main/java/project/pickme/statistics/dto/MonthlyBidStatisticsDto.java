package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyBidStatisticsDto {
	private String bidMonth;
	private int totalBids;
	private int totalUsers;
	private double competitionRate;
}
