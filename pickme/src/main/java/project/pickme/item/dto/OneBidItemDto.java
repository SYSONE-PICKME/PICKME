package project.pickme.item.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
