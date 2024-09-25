package project.pickme.statistics.service;

import java.util.HashMap;
import java.util.List;
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
		data.put("categoryCompetition", statisticsMapper.getCategoryCompetition());

		return data;
	}
}
