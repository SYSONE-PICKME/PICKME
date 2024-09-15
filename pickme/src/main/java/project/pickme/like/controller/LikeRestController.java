package project.pickme.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.like.service.LikeService;
import project.pickme.user.domain.User;

@RestController
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class LikeRestController {
	private final LikeService likeService;

	@PostMapping("/{id}/like")
	public ResponseEntity<?> switchLike(@CurrentUser User user, @PathVariable("id") Long id) {
		likeService.switchOrAddIfNotExist(id, user.getId());

		return ResponseEntity.ok("좋아요 요청 성공");
	}
}
