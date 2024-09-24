package project.pickme.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInfoDto {
	private String name;
	private String addr;
	private String email;
	private String phoneNum;
	private String businessNum;
}
