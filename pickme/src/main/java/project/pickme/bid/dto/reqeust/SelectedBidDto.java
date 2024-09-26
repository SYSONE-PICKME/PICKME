package project.pickme.bid.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedBidDto {
	private Long bidId;
	private long price;
	private String email;
	private String itemName;
	private String itemImage;
}
