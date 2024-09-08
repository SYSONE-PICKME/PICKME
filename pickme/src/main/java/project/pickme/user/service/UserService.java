package project.pickme.user.service;

import static project.pickme.user.constant.Type.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;
import project.pickme.user.dto.LoginDto;
import project.pickme.user.dto.SignUpDto;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	//TODO: 회원가입 전 아이디 중복확인 기능, 이메일 인증 기능

	@Transactional
	public void signUp(SignUpDto signUpDto){
		User user = createUser(signUpDto);
		userMapper.save(user);
	}

	private User createUser(SignUpDto signUpDto) {
		return User.builder()
			.id(signUpDto.getId())
			.password(passwordEncoder.encode(signUpDto.getPassword()))
			.role(Role.ROLE_USER)
			.name(signUpDto.getName())
			.email(signUpDto.getEmail() + "@" + signUpDto.getEmailDomain())
			.type(valueOf(signUpDto.getType().toUpperCase()))
			.addr(signUpDto.getAddr())
			.phoneNum(signUpDto.getPhoneNum())
			.businessNum(signUpDto.getBusinessNum())
			.build();
	}
}
