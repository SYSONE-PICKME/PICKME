package project.pickme.statistics.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.statistics.dto.CategoryCompetitionDto;
import project.pickme.statistics.dto.MonthlyBidStatisticsDto;

@Mapper
public interface StatisticsMapper {
	// 총 경매건(전체)
	int getTotalAuctions();

	// 총 경매(월별)
	List<MonthlyBidStatisticsDto> getMonthlyBids();

	// 전체 경쟁률
	double getTotalCompetitionRate();

	// 월별 경쟁률
	List<MonthlyBidStatisticsDto> getMonthlyCompetitionRate();

	// 카테고리별 경쟁률
	List<CategoryCompetitionDto> getCategoryCompetitionRate();
}
