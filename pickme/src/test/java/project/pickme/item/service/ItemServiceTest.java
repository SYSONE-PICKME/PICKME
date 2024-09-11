package project.pickme.item.service;

import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.pickme.item.constant.Status;
import project.pickme.item.domain.ItemDTO;
import project.pickme.item.domain.ItemFormDTO;

@SpringBootTest
@ActiveProfiles("test")
class ItemServiceTest {

	@Autowired
	ItemService itemService;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d H:mm:ss");


	// @Test
	// void isertItem() {
	// 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d a h:mm:ss");
	//
	// 	LocalDateTime startDate = LocalDateTime.parse("2024. 9. 15 오전 11:00:00", formatter);
	// 	LocalDateTime endDate = LocalDateTime.parse("2024. 9. 17 오전 12:00:00", formatter);
	//
	// 	ItemDTO itemDTO = new ItemDTO("Samsung smart monitorM7", "03","개인", 380000L, startDate, endDate, LocalDateTime.now(),
	// 		Status.NOT_OPEN, "changwon");
	//
	// 	itemService.save(itemDTO);
	// 	Assertions.assertEquals(itemDTO, itemService.findById(3L));
	//
	// 	}

	@Test
	void update() {

	}

	@Test
	void delete() {
		itemService.delete(2L);
	}
}