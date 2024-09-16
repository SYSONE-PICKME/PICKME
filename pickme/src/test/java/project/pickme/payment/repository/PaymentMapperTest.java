package project.pickme.payment.repository;

import static java.time.LocalDateTime.*;
import static project.pickme.item.constant.Status.*;
import static project.pickme.user.constant.Role.*;
import static project.pickme.user.constant.Type.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.bid.dto.response.BidCreateDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.payment.dto.SavePaymentDto;
import project.pickme.user.domain.Customs;
import project.pickme.user.domain.User;
import project.pickme.user.repository.CustomsMapper;
import project.pickme.user.repository.UserMapper;

@SpringBootTest
@ActiveProfiles("test")
class PaymentMapperTest {
	@Autowired private PaymentMapper paymentMapper;
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
		paymentMapper.deleteAll();
		bidMapper.deleteAll();
		itemMapper.deleteAll();
		userMapper.deleteAll();
		customsMapper.deleteAll();
	}

	@Test
	@DisplayName("포인트 결제 내역을 저장할 수 있다.")
	void save() {
	    // given
		BidCreateDto bidCreateDto = BidCreateDto.create(11000l, "testUser", itemId);
		bidMapper.save(bidCreateDto);

		SavePaymentDto savePaymentDto = SavePaymentDto.createOf("testUser", bidCreateDto.getBidId());

		// when //then
		paymentMapper.save(savePaymentDto);
	}

	private static User createUser(String id) {
		return User.builder()
			.id(id)
			.password("1234")
			.role(ROLE_USER)
			.name("김테스트")
			.email("test@naver.com")
			.type(USER)
			.addr("서울특별시 종로구 창경궁로")
			.phoneNum("010-1111-1111")
			.businessNum(null)
			.build();
	}
}
