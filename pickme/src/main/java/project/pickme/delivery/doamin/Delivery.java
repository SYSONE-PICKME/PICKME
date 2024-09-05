package project.pickme.delivery.doamin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.item.domain.Item;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Delivery {
	private Long id;
	private String invoiceNum;
	private String code;
	private Item item;
	private User user;
}
