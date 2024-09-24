package project.pickme.user.repository;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static project.pickme.item.constant.Status.*;
import static project.pickme.user.constant.Type.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.image.dto.ImageDto;
import project.pickme.image.repository.ImageMapper;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;
import project.pickme.user.domain.User;
import project.pickme.user.dto.customs.IncomeDto;

@SpringBootTest
@ActiveProfiles("test")
class CustomsMapperTest {
	@Autowired private BidMapper bidMapper;
	@Autowired private UserMapper userMapper;
	@Autowired private ItemMapper itemMapper;
	@Autowired private CustomsMapper customsMapper;
	@Autowired private ImageMapper imageMapper;

	@BeforeEach
	void setUp(){
		customsMapper.save(Customs.createCustoms("incheon", "1234", "incheon", "02-123-1234"));

		User user = createUser("testUser");
		userMapper.save(user);
	}

	@AfterEach
	void tearDown() {
		bidMapper.deleteAll();
		imageMapper.deleteAll();
		itemMapper.deleteAll();
		userMapper.deleteAll();
		customsMapper.deleteAll();
	}


	@Test
	@DisplayName("List로 전달받은 세관들을 한번에 저장할 수 있다.")
	void saveAll() {
	    // given
		List<Customs> customs = List.of(
			Customs.createCustoms("gunsan", "1234", "군산세관", "064-730-8710"),
			Customs.createCustoms("kimpo", "1234", "김포공항세관", "064-730-8710"),
			Customs.createCustoms("incheon", "1234", "인천세관", "064-730-8710"),
			Customs.createCustoms("changwon", "1234", "창원세관", "064-730-8710")
		);

	    // when
		customsMapper.saveAll(customs);

	    // then
		List<Customs> findAll = customsMapper.findAll();
		assertThat(findAll).hasSize(4)
			.extracting("id", "name", "tel")
			.containsExactlyInAnyOrder(Tuple.tuple("gunsan", "군산세관", "064-730-8710"),
				Tuple.tuple("kimpo", "김포공항세관", "064-730-8710"),
				Tuple.tuple("incheon", "인천세관", "064-730-8710"),
				Tuple.tuple("changwon", "창원세관", "064-730-8710"));
	}

	@Test
	@DisplayName("수입이 난 아이템과 누적 수입 총 수입을 확인할 수 있다.")
	void findIncomeItemById() {
	    // given
		String customsId = customsMapper.findByCustomsId("incheon").get().getId();
		createIncome();

		// when
		Pageable pageable = PageRequest.of(0, 6);
		List<IncomeDto> incomeDtos = customsMapper.findIncomeItemById(customsId, pageable);

		// then
		assertThat(incomeDtos).hasSize(2)
			.extracting("itemName", "income", "totalIncome")
			.containsExactly(
				Tuple.tuple("아이템2", 200000l, 1200000l),
				Tuple.tuple("아이템1", 1000000l, 1000000l));
	}

	private void createIncome() {
		ItemDto itemDto1 = new ItemDto("아이템1", 1, USER,10000l, now(), now(), CLOSED, "incheon");
		itemMapper.insertItem(itemDto1);

		ItemDto itemDto2 = new ItemDto("아이템2", 1, USER,10000l, now(), now().plusDays(1), CLOSED, "incheon");
		itemMapper.insertItem(itemDto2);

		imageMapper.insertImage(new ImageDto(itemDto1.getItemId(), "이미지1", "test1.png", 0));
		imageMapper.insertImage(new ImageDto(itemDto2.getItemId(), "이미지2", "test2.png", 0));

		User user = userMapper.findUserById("testUser").get();
		BidDto bid1 = BidDto.create(10000, user.getId(), itemDto1.getItemId());
		BidDto bid2 = BidDto.create(100000, user.getId(), itemDto1.getItemId());
		BidDto bid3 = BidDto.create(1000000, user.getId(), itemDto1.getItemId());
		BidDto bid4 = BidDto.create(200000, user.getId(), itemDto2.getItemId());

		bidMapper.save(bid1);
		bidMapper.save(bid2);
		bidMapper.save(bid3);
		bidMapper.save(bid4);

		bidMapper.updateBidSuccess(bid3.getBidId());
		bidMapper.updateBidSuccess(bid4.getBidId());
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
