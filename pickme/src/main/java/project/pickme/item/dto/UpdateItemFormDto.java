package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemFormDto {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long[] lawId;
}
