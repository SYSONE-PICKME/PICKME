package project.pickme.delivery.dto;

import lombok.Getter;

@Getter
public class ItemInfoDto {
	private String itemName;
	private String imgUrl;
	private String customsName;
	private long price;
	private String courier;
	private String invoiceNumber;
}
