package project.pickme.statistics.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.statistics.dto.CategoryCompetitionDto;
import project.pickme.statistics.dto.CategoryFailureRateDto;
import project.pickme.statistics.dto.CategoryFrequencyDto;
import project.pickme.statistics.dto.CompetitiveAuctionDto;
import project.pickme.statistics.dto.MonthlyBidStatisticsDto;

@Mapper
public interface StatisticsMapper {
	
	int getTotalAuctions();

	List<MonthlyBidStatisticsDto> getMonthlyBids();

	double getTotalCompetitionRate();

	List<MonthlyBidStatisticsDto> getMonthlyCompetitionRate();

	List<CategoryCompetitionDto> getCategoryCompetitionRate();

	List<CompetitiveAuctionDto> getTopCompetitiveAuctions();

	List<CategoryFrequencyDto> getCategoryRegistrationFrequency();

	List<CategoryFailureRateDto> getCategoryFailureRate();
}
