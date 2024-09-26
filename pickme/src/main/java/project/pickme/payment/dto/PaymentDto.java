package project.pickme.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
	private Long bidId;
	private long price;

	@Override
	public String toString() {
		return "PaymentDto{" +
			"bidId=" + bidId +
			", price=" + price +
			'}';
	}
}
