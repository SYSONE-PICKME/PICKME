package project.pickme.bid.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedBidDto {
	private Long itemId;
	private Long bidId;
	private String itemName;
	private String itemImage;
}
