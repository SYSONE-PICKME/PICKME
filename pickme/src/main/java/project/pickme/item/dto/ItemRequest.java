package project.pickme.item.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItemRequest {
	@Getter @Setter
	@NoArgsConstructor
	public static class Cursor {
		private Long itemId;
		private String status;

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime endTime;
		private String category;
	}

	@Getter @Setter
	@NoArgsConstructor
	public static class BidCursor {
		private Long itemId;

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime bidTime;
		private String category;
	}
}
