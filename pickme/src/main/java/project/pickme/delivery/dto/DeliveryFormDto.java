package project.pickme.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFormDto {
	private long itemId;
	private String userId;
	private String code;
	private String invoiceNumber;
}
