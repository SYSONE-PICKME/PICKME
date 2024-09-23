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
import project.pickme.charge.dto.ChargeDto;
import project.pickme.charge.repository.ChargeMapper;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.payment.dto.SavePaymentDto;
import project.pickme.payment.repository.PaymentMapper;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;
import project.pickme.user.domain.User;
import project.pickme.user.dto.PointHistoryDto;
import project.pickme.user.dto.UpdateInfoDto;

@SpringBootTest
@ActiveProfiles("test")
class UserMapperTest {
	
	@Autowired private UserMapper userMapper;
	@Autowired private ChargeMapper chargeMapper;
	@Autowired private ItemMapper itemMapper;
	@Autowired private BidMapper bidMapper;
	@Autowired private PaymentMapper paymentMapper;
	@Autowired private CustomsMapper customsMapper;

	private Long itemId;

	@BeforeEach
	void initUserData(){
		customsMapper.save(Customs.createCustoms("incheon", "1234", "incheon", "02-123-1234"));

		ItemDto itemDto = new ItemDto("테스트", 1, USER,10000l, now(), now(), CLOSED, "incheon");
		itemMapper.insertItem(itemDto);
		itemId = itemDto.getItemId();

		User user = createUser("initUser", 0);
		userMapper.save(user);
	}

	@AfterEach
	void tearDown() {
		paymentMapper.deleteAll();
		bidMapper.deleteAll();
		itemMapper.deleteAll();
		chargeMapper.deleteAll();
		userMapper.deleteAll();
		customsMapper.deleteAll();
	}

	@Test
	@DisplayName("회원 아이디로 회원을 찾을 수 있다.")
	void findUserById() {
	    // given // when
		User findUser = userMapper.findUserById("initUser").get();

		// then
		assertThat(findUser).extracting("id", "name", "email", "addr")
			.containsExactly("initUser", "김테스트", "test@naver.com","서울특별시 종로구 창경궁로");
	}

	@Test
	@DisplayName("회원을 저장할 수 있다.")
	void save() {
	    // given
		User user = createUser("test", 0);

		// when
		userMapper.save(user);

	    // then
		User findUser = userMapper.findUserById("test").get();
		assertThat(findUser).extracting("id", "name", "email", "addr")
			.containsExactly("test", "김테스트", "test@naver.com","서울특별시 종로구 창경궁로");
	}

	@Test
	@DisplayName("포인트를 차감할 수 있다.")
	void minusPoint() {
	    // given
		User user = createUser("pointUser", 100000);
		userMapper.save(user);
		User findUser = userMapper.findUserById("pointUser").get();
		long minus = 50000;

		// when
		userMapper.minusPoint(findUser.getId(), minus);

	    // then
		User afterMinusPointUser = userMapper.findUserById("pointUser").get();
		assertThat(afterMinusPointUser.getPoint()).isEqualTo(50000);
	}
	
	@Test
	@DisplayName("비밀번호를 업데이트 할 수 있다.")
	void updatePassword() {
	    // given
		User user = createUser("user", 10000);
		userMapper.save(user);
		String newPassword = "updatePassword";

		// when
		userMapper.updatePassword(newPassword, user.getId());
	    
	    // then
		User updatedUser = userMapper.findUserById(user.getId()).get();
		assertThat(updatedUser.getPassword()).isEqualTo(newPassword);
	}

	@Test
	@DisplayName("내 정보를 업데이트 할 수 있다.")
	void updateMyInfo() {
	    // given
		User user = createUser("user", 10000);
		userMapper.save(user);
		UpdateInfoDto updateInfoDto = new UpdateInfoDto("새로운 이름", "새로운 주소", user.getEmail(), user.getPhoneNum(), user.getBusinessNum());

	    // when
		userMapper.updateMyInfo(updateInfoDto, user.getId());

	    // then
		User updatedUser = userMapper.findUserById(user.getId()).get();
		assertThat(updatedUser).extracting("name", "addr")
			.containsExactly("새로운 이름", "새로운 주소");
	}
	
	@Test
	@DisplayName("포인트 결제 내역을 확인할 수 있다.")
	void findPointHistory() {
	    // given
		User user = createUser("user", 0);
		userMapper.save(user);

		chargeMapper.save(ChargeDto.Create.of(10000, user.getId())); //포인트 충전
		BidDto bidDto = BidDto.create(1000, user.getId(), itemId);
		bidMapper.save(bidDto);
		bidMapper.updateBidSuccess(bidDto.getBidId());

		paymentMapper.save(SavePaymentDto.createOf(user.getId(), bidDto.getBidId()));	//포인트 차감

	    // when
		Pageable pageable = PageRequest.of(0, 6);
		List<PointHistoryDto> pointHistory = userMapper.findPointHistory(user.getId(), pageable);

		// then
		assertThat(pointHistory).hasSize(2)
			.extracting("price", "type", "currentPoint")
			.containsExactly(Tuple.tuple(10000l, 1, 10000l),
				Tuple.tuple(1000l, 0, 9000l));
	}

	private static User createUser(String id, long point) {
		return User.builder()
			.id(id)
			.password("1234")
			.role(Role.ROLE_USER)
			.name("김테스트")
			.email("test@naver.com")
			.type(USER)
			.addr("서울특별시 종로구 창경궁로")
			.phoneNum("010-1111-1111")
			.point(point)
			.businessNum(null)
			.build();
	}
}
