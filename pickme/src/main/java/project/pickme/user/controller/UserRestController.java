package project.pickme.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserRestController {
	private final UserService userService;

	@PostMapping("/user/check-id")
	public ResponseEntity<?> checkDuplicateUserId(@RequestBody String id){
		userService.checkDuplicateId(id);
		return ResponseEntity.ok("사용 가능한 아이디");
	}
}
