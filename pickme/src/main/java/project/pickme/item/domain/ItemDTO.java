package project.pickme.item.domain;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
	private String name;
	private String code;
	private Type target;
	private Long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private String customsId;
}
