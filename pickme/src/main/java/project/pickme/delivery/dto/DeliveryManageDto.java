package project.pickme.delivery.dto;

import lombok.Getter;

@Getter
public class DeliveryManageDto {
	private long itemId;
	private String userId;
	private String name;
	private String imgUrl;
	private String invoiceNumber;
	private String deliveryCode;
	private long price;
	private String customsName;
}
