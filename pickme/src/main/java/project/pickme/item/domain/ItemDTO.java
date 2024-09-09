package project.pickme.item.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.customs.domain.Customs;
import project.pickme.item.constant.Status;


@Getter
@AllArgsConstructor
public class ItemDTO {
	private Long itemId;
	private String name;
	private String category;
	private String target;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime createTime;
	private Status status;
	private String customsId;
}
