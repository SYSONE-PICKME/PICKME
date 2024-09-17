package project.pickme.bid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
	private Long bidId;
	private long price;
	private String userId;
}
