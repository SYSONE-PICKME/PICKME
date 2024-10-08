package project.pickme.statistics.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.response.BaseResponse;
import project.pickme.statistics.service.StatisticsService;

@RestController
@RequestMapping("/user/api/statistics")
@RequiredArgsConstructor
public class StatisticsRestController {
	private final StatisticsService statisticsService;

	@GetMapping("/auction")
	public BaseResponse<Map<String, Object>> getStatisticsData() {
		Map<String, Object> statisticsData = statisticsService.getAllStatistics();
		return BaseResponse.ok(statisticsData);
	}

	@GetMapping("/item")
	public BaseResponse<Map<String, Object>> getItemStatistics() {
		Map<String, Object> statisticsData = statisticsService.getItemStatistics();
		return BaseResponse.ok(statisticsData);
	}

	@GetMapping("/finances")
	public BaseResponse<Map<String, Object>> getFinancesStatistics() {
		Map<String, Object> statisticsData = statisticsService.getFinancesStatistics();
		return BaseResponse.ok(statisticsData);
	}

	@GetMapping("/etc")
	public BaseResponse<Map<String, Object>> getEtcStatistics() {
		Map<String, Object> statisticsData = statisticsService.getEtcStatistics();
		return BaseResponse.ok(statisticsData);
	}

}
