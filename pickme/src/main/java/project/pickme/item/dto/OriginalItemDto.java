package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class OriginalItemDto {
	private String name;
	private int code;
	private String type;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	private String customsId;
}
