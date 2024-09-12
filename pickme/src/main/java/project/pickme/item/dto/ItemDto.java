package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
	private String name;
	private String code;
	private Type target;
	private Long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private String customsId;
}
