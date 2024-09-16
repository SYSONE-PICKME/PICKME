package project.pickme.common.auth;

import static project.pickme.common.exception.ErrorCode.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@RequiredArgsConstructor
@Service
public class CustomsDetailServiceImpl implements UserDetailsService {
	private final CustomsMapper customsMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Customs customs = customsMapper.findByCustomsId(id).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMS));
		return new CustomsDetailsImpl(customs);
	}
}
