package project.pickme.user.controller;


import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import project.pickme.user.service.UserService;

@WebMvcTest(UserRestController.class)
@ActiveProfiles("test")
class UserRestControllerTest {
	@Autowired private MockMvc mockMvc;
	@MockBean private UserService userService;

	@Test
	@DisplayName("아이디 중복 확인")
	@WithMockUser
	void checkDuplicateUserId() throws Exception {
	    // given
		String userId = "test";

	    // when // then
		mockMvc.perform(post("/user/check-id")
				.with(csrf())
				.content(userId)
				.contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(content().string("사용 가능한 아이디"));

		verify(userService, times(1)).checkDuplicateId(userId);
	}
}
