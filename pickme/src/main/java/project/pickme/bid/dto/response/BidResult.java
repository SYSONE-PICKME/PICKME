package project.pickme.bid.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BidResult {
	private final String type = "bidResult";
	private String result;

	public static BidResult success(){
		return new BidResult("success");
	}

	public static BidResult fail(){
		return new BidResult("fail");
	}
}
