package project.pickme.item.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.customs.domain.Customs;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
public class Item {
	private Long id;
	private String name;
	private Category category;
	private Type target;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private Customs customs;
}
