package project.pickme.user.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordDto {
	private String originPassword;
	private String newPassword;
}
