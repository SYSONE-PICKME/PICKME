package project.pickme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {
	@Getter
	@AllArgsConstructor
	public static class Info {
		private final String id;
		private final String name;
		private final long point;
	}

	@Getter
	@AllArgsConstructor
	public static class UpdatePoint {
		private final String id;
		private final long point;
	}
}
