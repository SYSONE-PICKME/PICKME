package project.pickme.statistics.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.response.BaseResponse;
import project.pickme.statistics.service.StatisticsService;

@RestController
@RequestMapping("/user/statistics")
@RequiredArgsConstructor
public class StatisticsRestController {
	private final StatisticsService statisticsService;

	@GetMapping("/toAuction")
	public BaseResponse<Map<String, Object>> getStatisticsData() {
		Map<String, Object> statisticsData = statisticsService.getAllStatistics();
		return BaseResponse.ok(statisticsData);
	}
}
