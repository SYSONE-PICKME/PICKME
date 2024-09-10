package project.pickme.bid.dto.request;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BidCreateDto {
	private long price;
	private LocalDateTime bidTime;
	private boolean isSuccess;
	private String userId;
	private Long itemId;

	public static BidCreateDto create(long price, String userId, Long itemId){
		return new BidCreateDto(price, LocalDateTime.now(), false, userId, itemId);
	}
}