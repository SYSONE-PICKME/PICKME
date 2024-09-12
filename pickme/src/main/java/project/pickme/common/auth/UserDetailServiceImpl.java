package project.pickme.common.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userMapper.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
		return new UserDetailsImpl(user);
	}
}
