package project.pickme.item.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.pickme.item.dto.LawDto;

@SpringBootTest
@ActiveProfiles("test")
class LawServiceTest {
	@Autowired
	private LawService lawService;

	@Test
	void getLaws() {
		List<LawDto> laws = lawService.getLaws();
		System.out.println(laws.size());
	}
}
