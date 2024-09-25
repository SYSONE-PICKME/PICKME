package project.pickme.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.pickme.statistics.service.StatisticsService;

@Controller
@RequestMapping("/user/statistics")
@RequiredArgsConstructor
public class StatisticsController {

	@GetMapping("/auctionStatistics")
	public String showStatisticsPage() {
		return "statistics/auctionStatistics";
	}
}
