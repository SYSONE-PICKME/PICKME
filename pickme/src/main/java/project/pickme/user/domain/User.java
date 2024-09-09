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

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", password='" + password + '\'' +
			", role=" + role +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", type=" + type +
			", addr='" + addr + '\'' +
			", phoneNum='" + phoneNum + '\'' +
			", businessNum='" + businessNum + '\'' +
			", point=" + point +
			'}';
	}
}
