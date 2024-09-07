package project.pickme.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.User;
import project.pickme.user.dto.UserSignUpDto;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	//TODO: 회원가입 전 아이디 중복확인 기능, 이메일 인증 기능

	@Transactional
	public void userSignUp(UserSignUpDto userSignUpDto){
		User user = User.createUser(userSignUpDto, passwordEncoder.encode(userSignUpDto.getPassword()));
		userMapper.save(user);
	}
}
