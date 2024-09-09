package project.pickme.customs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Customs {
	private String customs_id;
	private String password;
	private String role;
	private String name;
	private String tel;
}
