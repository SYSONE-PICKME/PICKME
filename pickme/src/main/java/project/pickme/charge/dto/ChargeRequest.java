package project.pickme.charge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChargeRequest {
	private String impUid;
	private String userId;
	private long point;
}
