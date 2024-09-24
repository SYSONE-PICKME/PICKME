package project.pickme.user.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.user.domain.Customs;
import project.pickme.user.dto.customs.IncomeDto;
import project.pickme.user.service.CustomsService;

@RestController
@RequestMapping("/customs")
@RequiredArgsConstructor
public class CustomsRestController {
	private final static int DEFAULT_PAGE_SIZE = 6;
	private final CustomsService customsService;

	@GetMapping("/income")
	public BaseResponse<?> findIncome(@CurrentUser Customs customs){
		List<IncomeDto> incomeDtos = customsService.findIncome(customs.getId());

		return BaseResponse.ok(incomeDtos);
	}
}
