package project.pickme.like.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.like.service.LikeService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class LikeRestController {
	private final LikeService likeService;

	/**
	 * 사용자의 특정 아이템에 대한 좋아요 상태를 토글하거나, 좋아요가 없을 경우 추가하는 메서드입니다.
	 *
	 * @param user 현재 인증된 사용자 정보입니다.
	 * @param id 좋아요를 토글할 아이템의 고유 식별자(ID)입니다.
	 * @return 성공적인 요청 수행을 나타내는 빈 {@link BaseResponse} 객체를 반환합니다.
	 */
	@PostMapping("/{id}/like")
	public BaseResponse<Void> switchLike(@CurrentUser User user, @PathVariable("id") Long id) {
		likeService.switchOrAddIfNotExist(id, user.getId());

		return BaseResponse.ok();
	}
}
