package project.pickme.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpDto {
	@NotBlank(message = "이메일은 필수입니다.")
	private String email;
	@NotBlank(message = "이메일 도메인은 필수입니다.")
	private String emailDomain;
	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;
	@NotBlank(message = "주소는 필수입니다.")
	private String addr;
	@NotBlank(message = "휴대폰 번호는 필수입니다.")
	private String phoneNum;
	@NotBlank(message = "이름은 필수입니다.")
	private String name;
	@NotBlank(message = "아이디는 필수입니다.")
	private String id;
	private String businessNum;
	private String type;
}
