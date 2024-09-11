package project.pickme.bid.repository;

import static org.assertj.core.api.Assertions.*;
import static project.pickme.user.constant.Type.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.BidCreateDto;
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

	@Test
	@DisplayName("저장된 입찰 아이디를 반환받을 수 있다.")
	void saveReturnId() {
		// given
		BidCreateDto bid = BidCreateDto.create(1000, "testUser", 2l);

		// when
		bidMapper.save(bid);

		// then
		assertThat(bid.getBidId()).isNotNull();
	}


	@Test
	@DisplayName("공매품에 입찰한 금액 가장 큰 금액을 찾을 수 있다.")
	void findMaxBidByItemId() {
	    // given
		User user = userMapper.findUserById("testUser").get();
		Long itemId = 1l;
		BidCreateDto bid1 = BidCreateDto.create(1000, user.getId(), itemId);
		BidCreateDto bid2 = BidCreateDto.create(10000, user.getId(), itemId);

		bidMapper.save(bid1);
		bidMapper.save(bid2);

	    // when
		long maxPrice = bidMapper.findMaxBidByItemId(itemId);

		// then
		assertThat(maxPrice).isEqualTo(10000);
	}

	@Test
	@DisplayName("입찰 아이디로 입찰을 찾을 수 있다.")
	void findBidById() {
	    // given
		User user = userMapper.findUserById("testUser").get();
		Long itemId = 1l;
		BidCreateDto bid1 = BidCreateDto.create(1000, user.getId(), itemId);
		BidCreateDto bid2 = BidCreateDto.create(10000, user.getId(), itemId);

		bidMapper.save(bid1);
		bidMapper.save(bid2);

	    // when
		Bid findBid = bidMapper.findBidById(bid1.getBidId()).get();

		// then
		assertThat(findBid).isNotNull()
			.extracting("id", "price", "user.id")
			.contains(bid1.getBidId(), 1000l, "testUser");
	}

	@TestFactory
	@DisplayName("입찰 상태 수정 시나리오")
	Collection<DynamicTest> updateBidSuccess(){
		//given
		User user = userMapper.findUserById("testUser").get();
		Long itemId = 1l;
		BidCreateDto bid = BidCreateDto.create(1000, user.getId(), itemId);
		bidMapper.save(bid);

		return List.of(
			DynamicTest.dynamicTest("입찰한 경우 처음 isSuccess 상태는 false이다.", () -> {
				//when //then
				Bid beforeUpdateBid = bidMapper.findBidById(bid.getBidId()).get();
				assertThat(beforeUpdateBid.isSuccess()).isFalse();
			}),
			DynamicTest.dynamicTest("updateBidSuccess를 하면 isSuccess 상태는 true이다.", () ->{
				//when
				bidMapper.updateBidSuccess(bid.getBidId());

				//then
				Bid afterUpdateBid = bidMapper.findBidById(bid.getBidId()).get();
				assertThat(afterUpdateBid.isSuccess()).isTrue();
			})
		);
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
