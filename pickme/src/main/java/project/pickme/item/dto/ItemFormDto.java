package project.pickme.item.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.user.constant.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFormDto {
	private String name;
	private int code;
	private Type type;
	private Long price;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private int law;
}
