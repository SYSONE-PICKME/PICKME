package project.pickme.item.repository;

import static org.assertj.core.api.Assertions.*;
import static project.pickme.user.constant.Type.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.item.domain.Item;

@SpringBootTest
@ActiveProfiles("test")
class ItemMapperTest {
	@Autowired private ItemMapper itemMapper;

	@BeforeEach
	void initData(){
		//TODO: 초기 아이템 저장
	}

	@AfterEach
	void tearDown() {
		//TODO: 아이템 삭제
	}

	@Test
	@DisplayName("아이템 아이디로 아이템 한개를 찾을 수 있다.")
	void findItemById() {
	    // given
	    Long itemId = 1l;

	    // when
		Item findItem = itemMapper.findItemById(itemId).get();

		// then
		assertThat(findItem)
			.extracting("name", "target", "price")
			.contains("공매품1", BUSINESS, 1000l);
	}
}
