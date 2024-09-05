package project.pickme.customs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customs {
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String deptName;
	private String tel;
}
