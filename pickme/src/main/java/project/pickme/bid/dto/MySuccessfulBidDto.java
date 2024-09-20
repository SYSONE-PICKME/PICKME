package project.pickme.bid.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MySuccessfulBidDto {
	private long itemId;
	private String name;
	private String imgUrl;
	private String invoiceNumber;
	private String deliveryCode;
	private long price;
	private LocalDateTime bidTime;
	private String customsName;
}
