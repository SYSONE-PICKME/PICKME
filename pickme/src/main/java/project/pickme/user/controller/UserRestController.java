package project.pickme.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.user.service.CustomsService;
import project.pickme.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserRestController {
	private final UserService userService;
	private final CustomsService customsService;

	@GetMapping("/user/check-id/{id}")
	public ResponseEntity<?> checkDuplicateUserId(@PathVariable("id") String id){
		userService.checkDuplicateId(id);
		return ResponseEntity.ok("사용 가능한 아이디");
	}
}
