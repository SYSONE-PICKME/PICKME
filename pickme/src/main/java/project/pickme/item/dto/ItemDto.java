package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {    //db저장용
	private long itemId;
	private String url;
	private String name;
	private int code;
	private Type type;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private String customsId;

	// itemId를 제외한 생성자
	public ItemDto(String name, int code, Type type, long price, LocalDateTime startTime, LocalDateTime endTime,
		Status status, String customsId) {
		this.name = name;
		this.code = code;
		this.type = type;
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.customsId = customsId;
	}
}
