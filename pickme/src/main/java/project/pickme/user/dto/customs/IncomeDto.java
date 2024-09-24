package project.pickme.user.dto.customs;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class IncomeDto {
	private String itemName;
	private String itemImage;
	private long income;
	private long totalIncome;
	private LocalDateTime endTime;
}
