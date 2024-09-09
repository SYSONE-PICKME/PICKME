package project.pickme.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.user.constant.Role;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Customs {
	private String id;
	private String password;
	private Role role;
	private String name;
	private String tel;

	public static Customs createCustoms(String id, String password, String name, String tel){
		return new Customs(id, password, Role.ROLE_ADMIN, name, tel);
	}

	@Override
	public String toString() {
		return "Customs{" +
			"id='" + id + '\'' +
			", password='" + password + '\'' +
			", role=" + role +
			", name='" + name + '\'' +
			", tel='" + tel + '\'' +
			'}';
	}
}
