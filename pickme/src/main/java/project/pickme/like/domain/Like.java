package project.pickme.like.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.item.domain.Item;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Like {
	private Long id;
	private boolean status;
	private Item item;
	private User user;
}
