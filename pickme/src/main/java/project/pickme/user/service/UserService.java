package project.pickme.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.User;
import project.pickme.user.dto.SignUpDto;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	//TODO: 회원가입 전 아이디 중복확인 기능, 이메일 인증 기능, 비밀번호 일치 기능

	@Transactional
	public void signUp(SignUpDto signUpDto){
		User user = User.createUser(signUpDto, passwordEncoder.encode(signUpDto.getPassword()));
		userMapper.save(user);
	}
}
