package project.pickme.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.pickme.user.domain.Customs;

@SpringBootTest
@ActiveProfiles("test")
class CustomsMapperTest {
	@Autowired private CustomsMapper customsMapper;

	@AfterEach
	void tearDown() {
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
}