package project.pickme.user.service;

import static project.pickme.user.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.user.constant.Role;
import project.pickme.user.constant.Type;
import project.pickme.user.domain.User;
import project.pickme.user.dto.PointHistoryDto;
import project.pickme.user.dto.UpdatePasswordDto;
import project.pickme.user.dto.SignUpDto;
import project.pickme.user.dto.UpdateInfoDto;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public void signUp(SignUpDto signUpDto) {
		User user = createUser(signUpDto);
		userMapper.save(user);
	}

	public void checkDuplicateId(String id) {
		userMapper.findUserById(id).ifPresent(user -> {
			throw new BusinessException(DUPLICATE_ID);
		});
	}

	@Transactional
	public void updatePassword(UpdatePasswordDto password, User user) {
		if(!passwordEncoder.matches(password.getOriginPassword(), user.getPassword())){
			throw new BusinessException(INVALID_PASSWORD);
		}

		userMapper.updatePassword(passwordEncoder.encode(password.getNewPassword()), user.getId());
	}

	@Transactional
	public void updateInfo(UpdateInfoDto updateInfoDto, User user) {
		userMapper.updateMyInfo(updateInfoDto, user.getId());
	}

	public List<PointHistoryDto> showHistory(User user) {
		return userMapper.findPointHistory(user.getId());
	}

	private User createUser(SignUpDto signUpDto) {
		return User.builder()
			.id(signUpDto.getId())
			.password(passwordEncoder.encode(signUpDto.getPassword()))
			.role(Role.ROLE_USER)
			.name(signUpDto.getName())
			.email(signUpDto.getEmail() + "@" + signUpDto.getEmailDomain())
			.type(Type.valueOf(signUpDto.getType().toUpperCase()))
			.addr(signUpDto.getAddr())
			.phoneNum(signUpDto.getPhoneNum())
			.businessNum(signUpDto.getBusinessNum())
			.build();
	}
}
