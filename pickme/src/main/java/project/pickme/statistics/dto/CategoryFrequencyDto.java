package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFrequencyDto {
	private String code;
	private String categoryName;
	private double categoryPercentage;
}
