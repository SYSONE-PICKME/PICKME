package project.pickme.bid.dto.response;

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
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String userId;

	public static OneBidItemDto createOf(Item item, User user, String imageUrl) {
		return OneBidItemDto.builder()
			.itemId(item.getId())
			.imageUrl(imageUrl)
			.name(item.getName())
			.startPrice(item.getPrice())
			.startTime(item.getStartTime())
			.endTime(item.getEndTime())
			.userId(user.getId())
			.build();
	}
}
