package project.pickme.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import project.pickme.common.auth.CustomAuthenticationSuccessHandler;
import project.pickme.common.auth.CustomsDetailServiceImpl;
import project.pickme.common.auth.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailServiceImpl userDetailService;
	private final CustomsDetailServiceImpl customsDetailService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(
				PathRequest.toStaticResources().atCommonLocations()
			);
	}

	@Bean
	public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		configureAuthenticationManager(managerBuilder, userDetailService);

		return commonSecurityFilter(http, "/user/**", "/user/loginForm", "/user/login", "/user/loginForm?error=true");
	}

	@Bean
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		configureAuthenticationManager(managerBuilder, customsDetailService);

		return commonSecurityFilter(http, "/customs/**", "/customs/loginForm", "/customs/login", "/customs/loginForm?error=true");
	}

	private void configureAuthenticationManager(AuthenticationManagerBuilder managerBuilder, UserDetailsService userDetailsService) throws Exception {
		managerBuilder.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder());
	}

	private SecurityFilterChain commonSecurityFilter(HttpSecurity http, String securityMatcher, String loginPage, String loginProcessingUrl, String failureRedirectUrl) throws Exception {
		http
			.securityMatcher(securityMatcher)
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(loginPage, "/user/signUp", "/user/signUpForm", "/user/check-id").permitAll()
				.requestMatchers("/customs/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasRole("USER")
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage(loginPage)
				.usernameParameter("id")
				.loginProcessingUrl(loginProcessingUrl)
				.successHandler(authenticationSuccessHandler())
				.failureHandler((request, response, exception) -> {
					response.sendRedirect(failureRedirectUrl);
				}).permitAll()
			)
			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true)
					.expiredUrl(loginPage));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
}
