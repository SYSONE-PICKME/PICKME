package project.pickme.item.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.item.dto.UpdateItemFormDto;
import project.pickme.item.service.ItemService;
import project.pickme.user.domain.Customs;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customs")
public class ItemRestController {
	private final ItemService itemService;

	/**
	 * 경매 아이템을 수정합니다.
	 *
	 * @param id 수정할 아이템의 ID
	 * @param form 아이템 수정에 필요한 정보가 담긴 DTO
	 * @param customs 현재 로그인된 사용자 정보 (@CurrentUser로 주입됨)
	 * @return 수정 성공 메시지를 담은 BaseResponse
	 */
	@PatchMapping("/edit/{id}")
	public BaseResponse<?> updateItem(@PathVariable("id") long id,
		@RequestBody UpdateItemFormDto form, @CurrentUser Customs customs) {
		itemService.updateItemByItemId(customs, form, id);

		return BaseResponse.ok("경매가 성공적으로 수정되었습니다.");
	}
}
