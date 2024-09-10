package project.pickme.common.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.Customs;

@RequiredArgsConstructor
@Getter
public class CustomsDetailsImpl implements UserDetails {
	private final Customs customs;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(customs.getRole().name()));
	}

	@Override
	public String getPassword() {
		return customs.getPassword();
	}

	@Override
	public String getUsername() {
		return customs.getId();
	}
}
