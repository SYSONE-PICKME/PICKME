package project.pickme.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PointHistoryDto {
	private LocalDateTime time;
	private long price;
	private int type;
}
