package project.pickme.bid.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.item.domain.Item;
import project.pickme.user.domain.User;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {
	private Long id;
	private long price;
	private LocalDateTime bidTime;
	private boolean isSuccess;
	private User user;
	private Item item;

	public String getUserId() {
		return user.getId();
	}

	public String getUserEmail(){
		return user.getEmail();
	}
}
