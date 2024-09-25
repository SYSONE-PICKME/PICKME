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
public class UpdateDto {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long itemId;
}
