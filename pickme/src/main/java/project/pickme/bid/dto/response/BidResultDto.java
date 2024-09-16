package project.pickme.bid.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BidResultDto {
	private final String type = "bidResult";
	private String result;

	public static BidResultDto success() {
		return new BidResultDto("success");
	}

	public static BidResultDto fail() {
		return new BidResultDto("fail");
	}
}
