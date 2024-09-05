package project.pickme.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.user.constant.Type;

@Getter
@AllArgsConstructor
public class User {
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String email;
	private Type type;
	private String address;
	private String phoneNum;
	private String businessNum;
	private long point;
}
