package project.pickme.user.dto;

import lombok.Data;

@Data
public class PasswordDto {
	private String originPassword;
	private String newPassword;
}
