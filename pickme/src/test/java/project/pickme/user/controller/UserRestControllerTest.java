package project.pickme.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.pickme.user.constant.Type.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;
import project.pickme.user.dto.user.UpdateInfoDto;
import project.pickme.user.repository.UserMapper;
import project.pickme.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserRestControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private UserMapper userMapper;

	@MockBean private UserService userService;

	@BeforeEach
	void initUser(){
		userMapper.save(createUser("testUser"));
	}

	@AfterEach
	void tearDown() {
		userMapper.deleteAll();
	}

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
			.andExpect(jsonPath("$.data").value("사용 가능한 아이디"));

		verify(userService, times(1)).checkDuplicateId(userId);
	}

	@Test
	@DisplayName("사용자 정보를 업데이트(이름, 주소, 메일, 전화번호, 사업자 번호)")
	@WithUserDetails(value = "testUser", userDetailsServiceBeanName = "userDetailServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
	void updateInfo() throws Exception {
	    // given
		UpdateInfoDto updateInfoDto = new UpdateInfoDto("testUser", "testaddr", "test@test.com", "010-1111-1111", null);

	    // when // then
		mockMvc.perform(put("/user/info")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateInfoDto)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

	private static User createUser(String id) {
		return User.builder()
			.id(id)
			.password("1234")
			.role(Role.ROLE_USER)
			.name("김테스트")
			.email("test@naver.com")
			.type(USER)
			.addr("서울특별시 종로구 창경궁로")
			.phoneNum("010-1111-1111")
			.point(0)
			.businessNum(null)
			.build();
	}
}
