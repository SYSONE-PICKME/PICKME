package project.pickme.statistics.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.statistics.repository.StatisticsMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {
	private final StatisticsMapper statisticsMapper;

	public Map<String, Object> getAllStatistics() {
		Map<String, Object> data = new HashMap<>();

		data.put("totalAuctions", statisticsMapper.getTotalAuctions());
		data.put("monthlyBids", statisticsMapper.getMonthlyBids());
		data.put("totalCompetitionRate", statisticsMapper.getTotalCompetitionRate());
		data.put("monthlyCompetitionRate", statisticsMapper.getMonthlyCompetitionRate());
		data.put("categoryCompetitionRate", statisticsMapper.getCategoryCompetitionRate());

		return data;
	}

	public Map<String, Object> getItemStatistics() {
		Map<String, Object> data = new HashMap<>();

		data.put("topCompetitiveAuctions", statisticsMapper.getTopCompetitiveAuctions());
		data.put("categoryFrequency", statisticsMapper.getCategoryRegistrationFrequency());
		data.put("categoryFailureRate", statisticsMapper.getCategoryFailureRate());

		return data;
	}

	public Map<String, Object> getFinancesStatistics() {
		Map<String, Object> data = new HashMap<>();

		data.put("categoryRevenue", statisticsMapper.getCategoryRevenue());
		data.put("categoryBidPriceRange", statisticsMapper.getCategoryBidPriceRange());
		data.put("categoryRevenueHistory", statisticsMapper.getCategoryRevenueHistory());

		return data;
	}

	public Map<String, Object> getEtcStatistics() {
		Map<String, Object> data = new HashMap<>();

		data.put("customsItemCount", statisticsMapper.getCustomsItemCount());
		data.put("hourlyBidActivity", statisticsMapper.getHourlyBidActivity());

		return data;
	}

}
