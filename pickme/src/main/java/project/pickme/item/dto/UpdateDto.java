package project.pickme.item.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;
	private long itemId;
}
