package project.pickme.customs.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.customs.domain.Customs;

@SpringBootTest
@ActiveProfiles("test")
class CustomsSeviceTest {


	@Autowired
	CustomsSevice customsService;

	@Test
	void tearDown() {
		customsService.deleteAll();
	}

	@Test
	void save() {
		Customs customs = new Customs("kimpo","kimpo1212","admin","김포공항세관","032-452-3533");
		customsService.save(customs);
		Customs result= customsService.findById(customs.getId());
		Assertions.assertEquals(customs.getId(),result.getId());
	}

	@Test
	void findById() {
		Customs result= customsService.findById("kimpo");
		Assertions.assertEquals("김포공항세관",result.getName());
	}
}
