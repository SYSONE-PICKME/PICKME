package project.pickme.delivery.doamin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.user.domain.User;

@Getter
@AllArgsConstructor
public class Delivery {
	private long deliveryId;
	private User user;
	private long itemId;
	private String invoiceNumber;
	private String code;
	private String status;
	private String courier;
}
