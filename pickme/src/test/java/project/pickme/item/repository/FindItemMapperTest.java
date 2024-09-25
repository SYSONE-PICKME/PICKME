package project.pickme.item.repository;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static project.pickme.item.constant.Status.*;
import static project.pickme.user.constant.Type.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.image.dto.ImageDto;
import project.pickme.image.repository.ImageMapper;
import project.pickme.item.domain.Item;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@SpringBootTest
@ActiveProfiles("test")
class FindItemMapperTest {
	@Autowired private FindItemMapper findItemMapper;
	@Autowired private ItemMapper itemMapper;
	@Autowired private CustomsMapper customsMapper;
	@Autowired private ImageMapper imageMapper;

	private Long itemId;

	@BeforeEach
	void initData(){
		customsMapper.save(Customs.createCustoms("incheon", "1234", "incheon", "02-123-1234"));

		ItemDto itemDto = new ItemDto("테스트", 1, USER,10000l, now(), now(), CLOSED, "incheon");
		itemMapper.insert(itemDto);
		itemId = itemDto.getItemId();

		imageMapper.insertImage(new ImageDto(itemId, "이미지1", "url1", 0));
		imageMapper.insertImage(new ImageDto(itemId, "이미지2", "url2", 1));
	}

	@AfterEach
	void tearDown() {
		imageMapper.deleteAll();
		itemMapper.deleteAll();
		customsMapper.deleteAll();
	}

	@Test
	@DisplayName("아이템 아이디로 아이템 한개를 찾을 수 있다.")
	void findItemById() {
	    // given // when
		Item findItem = findItemMapper.findItemById(itemId).get();

		// then
		assertThat(findItem)
			.extracting("name", "target", "price")
			.contains("테스트", USER, 10000l);
	}

	@Test
	@DisplayName("아이템 아이디로 첫번째 이미지와 함께 아이템을 찾을 수 있다.")
	void findItemByIdWithImage() {
	    // given // when
		OneBidItemDto find = findItemMapper.findItemByIdWithImage(itemId);

		// then
		assertThat(find).extracting("itemId", "imageUrl")
			.containsExactly(itemId, "url1");
	}
}
