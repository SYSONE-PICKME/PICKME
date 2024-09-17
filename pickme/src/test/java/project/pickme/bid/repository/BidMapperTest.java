package project.pickme.bid.repository;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static project.pickme.item.constant.Status.*;
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

import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.dto.response.PriceDto;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;
import project.pickme.user.domain.User;
import project.pickme.user.repository.CustomsMapper;
import project.pickme.user.repository.UserMapper;

@SpringBootTest
@ActiveProfiles("test")
class BidMapperTest {
	@Autowired private BidMapper bidMapper;
	@Autowired private UserMapper userMapper;
	@Autowired private ItemMapper itemMapper;
	@Autowired private CustomsMapper customsMapper;

	private Long itemId;

	@BeforeEach
	void setUp(){
		customsMapper.save(Customs.createCustoms("incheon", "1234", "incheon", "02-123-1234"));

		ItemDto itemDto = new ItemDto("테스트", 1, USER,10000l, now(), now(), CLOSED, "incheon");
		itemMapper.insertItem(itemDto);
		itemId = itemDto.getItemId();

		User user = createUser("testUser");
		userMapper.save(user);
	}

	@AfterEach
	void tearDown() {
		bidMapper.deleteAll();
		itemMapper.deleteAll();
		userMapper.deleteAll();
		customsMapper.deleteAll();
	}

	@Test
	@DisplayName("입찰을 저장할 수 있다.")
	void save() {
	    // given
		BidDto bid = BidDto.create(1000, "testUser", itemId);

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
		BidDto bid = BidDto.create(1000, "testUser", itemId);

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
		BidDto bid1 = BidDto.create(1000, user.getId(), itemId);
		BidDto bid2 = BidDto.create(10000, user.getId(), itemId);

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
		BidDto bid1 = BidDto.create(1000, user.getId(), itemId);
		BidDto bid2 = BidDto.create(10000, user.getId(), itemId);

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
		BidDto bid = BidDto.create(1000, user.getId(), itemId);
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

	@Test
	@DisplayName("해당 공매품에 입찰한 모든 가격 정보를 가져올 수 있다.(오름차순)")
	void findAllPriceByItemId() {
	    // given
		User user = userMapper.findUserById("testUser").get();
		BidDto bid1 = BidDto.create(1000, user.getId(), itemId);
		BidDto bid2 = BidDto.create(10000, user.getId(), itemId);
		BidDto bid3 = BidDto.create(100000, user.getId(), itemId);

		bidMapper.save(bid1);
		bidMapper.save(bid2);
		bidMapper.save(bid3);

	    // when
		List<PriceDto> allPrices = bidMapper.findAllPriceByItemId(itemId);

		// then
		assertThat(allPrices).hasSize(3)
			.extracting(PriceDto::getPrice, PriceDto::getUserId)
			.containsExactly(
				tuple(1000L, "testUser"),
				tuple(10000L, "testUser"),
				tuple(100000L, "testUser")
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
