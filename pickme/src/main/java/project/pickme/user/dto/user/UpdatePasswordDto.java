package project.pickme.user.dto.user;

import lombok.Getter;

@Getter
public class UpdatePasswordDto {
	private String originPassword;
	private String newPassword;
}
