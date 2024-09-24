package project.pickme.user.dto.user;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PointHistoryDto {
	private LocalDateTime time;
	private long price;
	private int type;
	private long currentPoint;
}
