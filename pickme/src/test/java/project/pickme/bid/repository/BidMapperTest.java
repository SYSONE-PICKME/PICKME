package project.pickme.bid.repository;

import static org.assertj.core.api.Assertions.*;
import static project.pickme.user.constant.Type.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.request.BidCreateDto;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@SpringBootTest
@ActiveProfiles("test")
class BidMapperTest {
	@Autowired private BidMapper bidMapper;
	@Autowired private UserMapper userMapper;

	@BeforeEach
	void setUp(){
		//TODO: 공매 품 저장 해야함 지금은 db에 데이터 넣었음
		User user = createUser("testUser");
		userMapper.save(user);
	}

	@AfterEach
	void tearDown() {
		bidMapper.deleteAll();
		userMapper.deleteAll();
	}

	@Test
	@DisplayName("입찰을 저장할 수 있다.")
	void save() {
	    // given
		BidCreateDto bid = BidCreateDto.create(1000, "testUser", 1l);

		// when
		bidMapper.save(bid);

	    // then
		List<Bid> findBids = bidMapper.findAll();
		assertThat(findBids).hasSize(1)
			.extracting("price", "user.id", "isSuccess")
			.containsExactlyInAnyOrder(tuple(1000l, "testUser", false));
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
			.businessNum(null)
			.build();
	}
}