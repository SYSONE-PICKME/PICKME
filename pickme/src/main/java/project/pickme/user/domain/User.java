package project.pickme.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
public class User {
	private String id;
	private String password;
	private String role;
	private String name;
	private String email;
	private Type type;
	private String address;
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
