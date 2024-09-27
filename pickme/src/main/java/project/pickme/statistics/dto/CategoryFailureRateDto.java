package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFailureRateDto {
	private String categoryCode;
	private String categoryName;
	private int failedAuctions;
	private double failRate;
}
