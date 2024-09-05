package project.pickme.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.bid.domain.Bid;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Payment {
	private Long id;
	private User user;
	private Bid bid;
}
