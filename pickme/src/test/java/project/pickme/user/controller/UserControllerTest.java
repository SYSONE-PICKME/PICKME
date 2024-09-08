package project.pickme.user.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.pickme.user.dto.SignUpDto;
import project.pickme.user.service.UserService;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	@MockBean UserService userService;

	@Test
	@DisplayName("정상적으로 회원가입이 된 경우 /user/login로 리다이렉트 한다.")
	@WithMockUser
	void signUpSuccess() throws Exception {
	    // given
		SignUpDto signUpDto = new SignUpDto("email", "emailDomain", "password", "addr",
			"phoneNum", "name", "id", null, "user");

		// then
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpDto", signUpDto)
				.with(csrf()))  // CSRF 토큰 포함
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"));
	}

	@Test
	@DisplayName("회원가입 폼에 빈칸이 있는 경우 회원가입이 되지 않는다.")
	@WithMockUser
	void signUpFail() throws Exception {
		// given
		SignUpDto signUpDto = new SignUpDto("email", "emailDomain", "password", "addr",
			null, null, "id", null, "user");

		// when //then
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpDto", signUpDto)
				.with(csrf()))
			.andExpect(view().name("user/signUpForm"))
			.andExpect(model().attributeHasFieldErrors("signUpDto", "phoneNum", "name"));
	}
}