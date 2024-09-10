package project.pickme.common.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import project.pickme.common.auth.CustomsDetailsImpl;
import project.pickme.common.auth.UserDetailsImpl;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentUser.class) != null;
	}
	//
	// @Override
	// public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	// 	NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//
	// 	if(authentication == null || authentication.getPrincipal().equals("anonymousUser")){
	// 		return null;
	// 	}
	//
	// 	UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	//
	// 	if(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))){
	// 		return ((CustomsDetailsImpl)userDetails).getCustoms();
	// 	} else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
	// 		return ((UserDetailsImpl)userDetails).getUser();
	// 	} else{
	// 		return null;
	// 	}
	// }

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated() ||
			"anonymousUser".equals(authentication.getPrincipal())) {
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (!(principal instanceof UserDetails)) {
			return null; // 예상치 못한 principal 타입에 대한 처리
		}

		UserDetails userDetails = (UserDetails) principal;

		if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			if (userDetails instanceof CustomsDetailsImpl) {
				return ((CustomsDetailsImpl) userDetails).getCustoms();
			}
		} else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
			if (userDetails instanceof UserDetailsImpl) {
				return ((UserDetailsImpl) userDetails).getUser();
			}
		}

		return null; // 처리되지 않은 경우 null 반환
	}

}
