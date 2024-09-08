package project.pickme.user.repository;

import static org.assertj.core.api.Assertions.*;
import static project.pickme.user.constant.Type.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;

@SpringBootTest
@ActiveProfiles("test")
class UserMapperTest {
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
	@DisplayName("회원 아이디로 회원을 찾을 수 있다.")
	void findById() {
	    // given // when
		User findUser = userMapper.findById("initUser").get();

		// then
		assertThat(findUser).extracting("id", "name", "email", "addr")
			.containsExactly("initUser", "김테스트", "test@naver.com","서울특별시 종로구 창경궁로");
	}

	@Test
	@DisplayName("회원을 저장할 수 있다.")
	void save() {
	    // given
		User user = createUser("test");

		// when
		userMapper.save(user);

	    // then
		User findUser = userMapper.findById("test").get();
		assertThat(findUser).extracting("id", "name", "email", "addr")
			.containsExactly("test", "김테스트", "test@naver.com","서울특별시 종로구 창경궁로");
	}

	private static User createUser(String id) {
		return User.builder()
			.id(id)
			.password("1234")
			.role(Role.USER)
			.name("김테스트")
			.email("test@naver.com")
			.type(USER)
			.addr("서울특별시 종로구 창경궁로")
			.phoneNum("010-1111-1111")
			.businessNum(null)
			.build();
	}
}
