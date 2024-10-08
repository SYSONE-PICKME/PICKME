package project.pickme.item.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.user.constant.Type;

public class FindItemDto {
	@Getter
	@NoArgsConstructor
	public static class GetOne {
		private Long itemId;
		private String itemName;
		private String target;
		private Long price;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String itemStatus;

		private String customsId;
		private String customsName;
		private String customsTel;

		private String images;
		private boolean isLiked;
	}

	@Getter
	public static class Info {
		private final Long id;
		private final String name;
		private final String target;
		private final Long price;
		private final LocalDateTime startTime;
		private final LocalDateTime endTime;
		private final String status;

		private final String customsId;
		private final String customsName;
		private final String customsTel;

		private final List<String> imageUrls;
		private final boolean isLiked;

		private final List<LawDto> laws;

		public Info(GetOne dto, List<LawDto> laws) {
			this.id = dto.getItemId();
			this.name = dto.getItemName();
			this.target = Type.valueOf(dto.getTarget()).getValue();
			this.price = dto.getPrice();
			this.startTime = dto.getStartTime();
			this.endTime = dto.getEndTime();
			this.status = dto.getItemStatus();
			this.customsId = dto.getCustomsId();
			this.customsName = dto.getCustomsName();
			this.customsTel = dto.getCustomsTel();
			this.imageUrls = Arrays.asList(dto.getImages().split(","));
			this.isLiked = dto.isLiked();
			this.laws = laws;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class GetAll {
		private Long id;
		private String name;
		private long price;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String status;
		private String imgUrl;
		private Boolean isLiked;
	}

	@Getter
	@NoArgsConstructor
	public static class WishList {
		private Long id;
		private String name;
		private long price;
		private LocalDateTime endTime;
		private String imgUrl;
		private String customsName;
		private Boolean isLiked;
	}

	@Getter
	@NoArgsConstructor
	public static class MyBid {
		private Long id;
		private String name;
		private long price;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String status;
		private String imgUrl;
		private Boolean isLiked;
		private LocalDateTime latestBidTime;
	}
}
