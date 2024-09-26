package project.pickme.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/statistics")
@RequiredArgsConstructor
public class StatisticsController {

	@GetMapping
	public String showDashboardPage() { return "statistics/dashboard"; }

	@GetMapping("/auction")
	public String showAuctionPage() {
		return "statistics/auction";
	}

	@GetMapping("/item")
	public String showItemPage() {
		return "statistics/item";
	}
}
