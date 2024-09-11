package project.pickme.bid.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.pickme.item.domain.Item;
import project.pickme.user.domain.User;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OneBidItemDto {
	private Long itemId;
	private String imageUrl;
	private String name;
	private Long startPrice;
	private Long maxPrice;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String userId;
	private long myPoint;

	public static OneBidItemDto of(Item item, User user, Long maxPrice, String imageUrl) {
		return OneBidItemDto.builder()
			.itemId(item.getId())
			.imageUrl(imageUrl)
			.name(item.getName())
			.startPrice(item.getPrice())
			.startTime(item.getStartTime())
			.maxPrice(maxPrice == null ? 0 : maxPrice)
			.endTime(item.getEndTime())
			.userId(user.getId())
			.myPoint(user.getPoint())
			.build();
	}
}
