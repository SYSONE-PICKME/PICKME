package project.pickme.item.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFormDto {
	private String name;
	private String code;
	private Type target;
	private Long price;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private int law;
}
