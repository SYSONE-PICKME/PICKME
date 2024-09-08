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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import project.pickme.common.auth.CustomAuthenticationSuccessHandler;
import project.pickme.common.auth.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailServiceImpl userDetailService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){	//정적 리소스 시큐리티 대상 제외
		return (web) -> {
			web.ignoring()
				.requestMatchers(
					PathRequest.toStaticResources().atCommonLocations()
				);
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		managerBuilder.userDetailsService(userDetailService)
			.passwordEncoder(bCryptPasswordEncoder());

		http
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/user/**", "/").permitAll()
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/user/loginForm")
				.usernameParameter("id")
				.loginProcessingUrl("/user/login")
				.successHandler(authenticationSuccessHandler())
				.failureHandler((request, response, exception) -> {
					response.sendRedirect("/user/loginForm?error=true");
				}).permitAll()
			)

			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
					.maximumSessions(1));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(){
		return new CustomAuthenticationSuccessHandler();
	}
}
