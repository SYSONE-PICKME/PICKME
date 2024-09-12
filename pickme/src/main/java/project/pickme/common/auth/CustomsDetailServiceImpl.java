package project.pickme.common.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@RequiredArgsConstructor
@Service
public class CustomsDetailServiceImpl implements UserDetailsService {
	private final CustomsMapper customsMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Customs customs = customsMapper.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 세관"));
		return new CustomsDetailsImpl(customs);
	}
}
