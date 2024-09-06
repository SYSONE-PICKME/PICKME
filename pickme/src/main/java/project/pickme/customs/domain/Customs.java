package project.pickme.customs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customs {
	private String id;
	private String password;
	private String role;
	private String name;
	private String deptName;
	private String tel;
}
