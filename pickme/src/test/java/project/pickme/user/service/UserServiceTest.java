package project.pickme.user.service;

import static project.pickme.user.constant.Type.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.common.exception.BusinessException;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;
import project.pickme.user.exception.UserErrorCode;
import project.pickme.user.repository.UserMapper;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
	@Autowired private UserService userService;
	@Autowired private UserMapper userMapper;

	@BeforeEach
	void initUserData(){
		User user = createUser("initUser");
		userMapper.save(user);
	}

	@AfterEach
	void tearDown() {
		userMapper.deleteAll();
	}

	@Test
	@DisplayName("아이디가 중복인 경우 예외가 발생한다.")
	void checkDuplicateId() {
		// given
		String userId = "initUser";

		// when // then
		Assertions.assertThatThrownBy(() -> userService.checkDuplicateId(userId))
			.isInstanceOf(BusinessException.class)
			.hasFieldOrPropertyWithValue("errorCode", UserErrorCode.DUPLICATE_ID);
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
