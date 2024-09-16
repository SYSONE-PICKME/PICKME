package project.pickme.item.repository;

import static java.time.LocalDateTime.*;
import static project.pickme.item.constant.Status.*;
import static project.pickme.user.constant.Type.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.item.domain.Item;
import project.pickme.item.dto.ItemDto;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@SpringBootTest
@ActiveProfiles("test")
class ItemMapperTest {
	@Autowired private ItemMapper itemMapper;
	@Autowired private CustomsMapper customsMapper;

	private Long itemId;

	@BeforeEach
	void initData(){
		customsMapper.save(Customs.createCustoms("incheon", "1234", "incheon", "02-123-1234"));

		ItemDto itemDto = new ItemDto("테스트", 1, USER,10000l, now(), now(), CLOSED, "incheon");
		itemMapper.insertItem(itemDto);
		itemId = itemDto.getItemId();
	}

	@AfterEach
	void tearDown() {
		itemMapper.deleteAll();
		customsMapper.deleteAll();
	}

	@Test
	@DisplayName("아이템 아이디로 아이템 한개를 찾을 수 있다.")
	void findItemById() {
	    // given // when
		Item findItem = itemMapper.findItemById(itemId).get();

		// then
		Assertions.assertThat(findItem)
			.extracting("name", "target", "price")
			.contains("테스트", USER, 10000l);
	}
}
