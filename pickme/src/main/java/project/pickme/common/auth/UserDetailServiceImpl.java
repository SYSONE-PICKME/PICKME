package project.pickme.common.auth;

import static project.pickme.common.exception.ErrorCode.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userMapper.findUserById(id).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
		return new UserDetailsImpl(user);
	}
}
