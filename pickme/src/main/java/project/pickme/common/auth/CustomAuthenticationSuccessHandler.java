package project.pickme.common.auth;

import static project.pickme.user.constant.Role.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final Map<String, String> redirectUrlMap = Map.of(
		ROLE_ADMIN.name(), "/customs/home",
		ROLE_USER.name(), "/user/home"
	);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException{
		GrantedAuthority authority = authentication.getAuthorities().iterator().next();

		response.sendRedirect(redirectUrlMap.getOrDefault(authority.getAuthority(), "/"));
	}
}
