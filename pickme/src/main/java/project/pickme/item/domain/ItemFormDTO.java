package project.pickme.item.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFormDTO {
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

