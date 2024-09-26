package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCompetitionDto {
	private String categoryCode;
	private int totalBids;
	private int totalUsers;
	private double competitionRate;
}
