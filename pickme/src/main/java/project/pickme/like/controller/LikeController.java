package project.pickme.like.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.like.service.LikeService;
import project.pickme.user.domain.User;

@Controller
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class LikeController {
	private final LikeService likeService;

	@PostMapping("/{id}/like")
	public String switchLike(@CurrentUser User user, @PathVariable("id") Long id) {
		likeService.switchOrAddIfNotExist(id, user.getId());

		return "redirect:/user/item/" + id;
	}
}
