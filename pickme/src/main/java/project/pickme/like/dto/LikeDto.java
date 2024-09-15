package project.pickme.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
	private Long id;
	private boolean status;
	private Long itemId;
	private String userId;
}
