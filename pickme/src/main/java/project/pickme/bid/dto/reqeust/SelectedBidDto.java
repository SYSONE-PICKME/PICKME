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

	@Override
	public String toString() {
		return "SelectedBidDto{" +
			"bidId=" + bidId +
			", price=" + price +
			", email='" + email + '\'' +
			", itemName='" + itemName + '\'' +
			", itemImage='" + itemImage + '\'' +
			'}';
	}
}
