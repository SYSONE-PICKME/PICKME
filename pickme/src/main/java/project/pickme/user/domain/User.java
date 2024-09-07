package project.pickme.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.pickme.user.constant.Role;
import project.pickme.user.constant.Type;
import project.pickme.user.dto.SignUpDto;

@Getter
@AllArgsConstructor
@Builder
public class User{
	private String id;
	private String password;
	private Role role;
	private String name;
	private String email;
	private Type type;
	private String addr;
	private String phoneNum;
	private String businessNum;
	private long point;

	public static User createUser(SignUpDto signUpDto, String password){
		return User.builder()
			.id(signUpDto.getId())
			.password(password)
			.role(Role.USER)
			.name(signUpDto.getName())
			.email(signUpDto.getEmail() + "@" + signUpDto.getEmailDomain())
			.type(signUpDto.getBusinessNum() == null ? Type.USER : Type.BUSINESS)
			.addr(signUpDto.getAddr())
			.phoneNum(signUpDto.getPhoneNum())
			.businessNum(signUpDto.getBusinessNum())
			.build();
	}
}
