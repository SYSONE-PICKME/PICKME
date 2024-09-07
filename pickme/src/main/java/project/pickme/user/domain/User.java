package project.pickme.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.pickme.user.constant.Role;
import project.pickme.user.constant.Type;
import project.pickme.user.dto.UserSignUpDto;

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

	public static User createUser(UserSignUpDto userSignUpDto, String password){
		return User.builder()
			.id(userSignUpDto.getId())
			.password(password)
			.role(Role.USER)
			.name(userSignUpDto.getName())
			.email(userSignUpDto.getEmail() + "@" + userSignUpDto.getEmailDomain())
			.type(Type.USER)
			.addr(userSignUpDto.getAddr())
			.phoneNum(userSignUpDto.getPhoneNum())
			.build();
	}
}
