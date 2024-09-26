package project.pickme.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRevenueHistoryDto {
	private String categoryName;
	private String month;
	private long monthlyRevenue;
}
