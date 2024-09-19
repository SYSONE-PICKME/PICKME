package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SuccessfullCustomsItemDto {
	private long itemId;
	private String name;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String imgUrl;
	private long price;
	private String customsName;
	private String status;
}
