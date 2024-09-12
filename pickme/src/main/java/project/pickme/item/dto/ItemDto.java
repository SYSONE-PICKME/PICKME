package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
public class ItemDto {	//db저장용
	private String name;
	private int code;
	private Type type;
	private Long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private String customsId;
}
