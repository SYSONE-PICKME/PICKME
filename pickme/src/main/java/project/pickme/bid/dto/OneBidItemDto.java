package project.pickme.bid.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	private List<Long> allPrice;
	private Long maxPrice;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String userId;
	private long myPoint;

	public static OneBidItemDto of(Item item, User user, List<Long> allPrice, String imageUrl) {
		return OneBidItemDto.builder()
			.itemId(item.getId())
			.imageUrl(imageUrl)
			.name(item.getName())
			.startPrice(item.getPrice())
			.allPrice(allPrice)
			.maxPrice(allPrice.isEmpty() ? 0 : allPrice.getLast())
			.startTime(item.getStartTime())
			.endTime(item.getEndTime())
			.userId(user.getId())
			.myPoint(user.getPoint())
			.build();
	}
}
