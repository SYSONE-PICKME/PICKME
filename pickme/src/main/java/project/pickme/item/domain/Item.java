package project.pickme.item.domain;

import static project.pickme.item.constant.Status.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.user.domain.Customs;
import project.pickme.item.constant.Status;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	private Long id;
	private String name;
	private Type target;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;
	private Customs customs;

	public boolean isOpen() {
		return status.equals(OPEN);
	}
}
