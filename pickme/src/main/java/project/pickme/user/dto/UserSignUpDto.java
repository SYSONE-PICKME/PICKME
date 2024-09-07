package project.pickme.user.dto;

import lombok.Data;

@Data
public class UserSignUpDto {
	private String email;
	private String emailDomain;
	private String password;
	private String addr;
	private String phoneNum;
	private String name;
	private String id;
}
