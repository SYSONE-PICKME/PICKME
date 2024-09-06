package project.pickme.charge.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Charge {
	private Long id;
	private long price;
	private LocalDateTime chargeTime;
	private User user;
}
