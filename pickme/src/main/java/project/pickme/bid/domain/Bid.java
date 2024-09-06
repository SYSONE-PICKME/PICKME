package project.pickme.bid.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.item.domain.Item;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Bid {
	private Long id;
	private long price;
	private LocalDateTime bidTime;
	private boolean isSuccess;
	private User user;
	private Item item;
}
