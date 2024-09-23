package project.pickme.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.user.domain.User;
import project.pickme.user.dto.PointHistoryDto;
import project.pickme.user.dto.UpdatePasswordDto;
import project.pickme.user.dto.UpdateInfoDto;
import project.pickme.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	private static final int DEFAULT_PAGE_SIZE = 6;
	private final UserService userService;

	/**
	 * 아이디 중복확인 메서드입니다.
	 * 
	 * @param id 중복확인 할 아이디
	 * @return 중복확인 결과 메세지
	 */
	@PostMapping("/check-id")
	public BaseResponse<?> checkDuplicateUserId(@RequestBody String id) {
		userService.checkDuplicateId(id);

		return BaseResponse.ok("사용 가능한 아이디");
	}

	/**
	 * 개인정보를 수정하는 메서드입니다.
	 * 
	 * @param updateInfoDto 수정할 개인정보를 담은 dto
	 * @param user 로그인 된 사용자
	 * @return 성공 여부
	 */
	@PutMapping("/info")
	public BaseResponse<?> updateInfo(@RequestBody UpdateInfoDto updateInfoDto, @CurrentUser User user){
		userService.updateInfo(updateInfoDto, user);

		return BaseResponse.ok();
	}

	/**
	 * 비밀번호를 수정하는 메서드입니다.
	 * 
	 * @param updatePasswordDto 현재 비밀번호, 새로운 비밀번호를 담은 dto
	 * @param user 로그인 된 사용자
	 * @return 성공 여부
	 */
	@PutMapping("/password")
	public BaseResponse<?> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto, @CurrentUser User user){
		userService.updatePassword(updatePasswordDto, user);

		return BaseResponse.ok();
	}

	/**
	 * 포인트 사용 내역을 조회하는 메서드입니다.
	 * 
	 * @param user 로그인 된 사용자
	 * @param pageable 페이지네이션 정보
	 * @return 포인트 사용내역, 누적합계 정보
	 */
	@GetMapping("/point/history")
	public BaseResponse<?> showPointHistory(@CurrentUser User user, @PageableDefault(size = DEFAULT_PAGE_SIZE, page = 0) Pageable pageable) {
		Page<PointHistoryDto> pointHistories = userService.showHistory(user, pageable);
		return BaseResponse.ok(pointHistories);
	}
}
