package project.pickme.customs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Customs {
	private String id;
	private String password;
	private String role;
	private String name;
	private String tel;
}
