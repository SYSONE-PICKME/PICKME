package project.pickme.user.dto;

import lombok.Data;

@Data
public class SignUpDto {
	private String email;
	private String emailDomain;
	private String password;
	private String addr;
	private String phoneNum;
	private String name;
	private String id;
	private String businessNum;
	private String type;
}
