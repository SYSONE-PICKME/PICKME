package project.pickme.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.dto.UpdateItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.domain.Customs;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customs")
public class ItemRestController {
	private final ItemService itemService;

	@PatchMapping("/edit/{id}")
	public ResponseEntity<?> updateItem(@PathVariable("id") long id,
		@RequestBody UpdateItemFormDto form, @CurrentUser Customs customs) {
		itemService.updateItemByItemId(customs, form, id);
		return ResponseEntity.ok().body("경매가 성공적으로 수정되었습니다.");
	}
}
