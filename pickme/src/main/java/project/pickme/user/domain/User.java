package project.pickme.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.user.constant.Role;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
