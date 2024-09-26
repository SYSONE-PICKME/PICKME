package project.pickme.statistics.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.statistics.dto.CategoryBidPriceRangeDto;
import project.pickme.statistics.dto.CategoryCompetitionDto;
import project.pickme.statistics.dto.CategoryFailureRateDto;
import project.pickme.statistics.dto.CategoryFrequencyDto;
import project.pickme.statistics.dto.CategoryRevenueDto;
import project.pickme.statistics.dto.CategoryRevenueHistoryDto;
import project.pickme.statistics.dto.CompetitiveAuctionDto;
import project.pickme.statistics.dto.CustomsItemCountDto;
import project.pickme.statistics.dto.HourlyBidActivityDto;
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

	List<CategoryRevenueDto> getCategoryRevenue();

	List<CategoryBidPriceRangeDto> getCategoryBidPriceRange();

	List<CategoryRevenueHistoryDto> getCategoryRevenueHistory();

	List<CustomsItemCountDto> getCustomsItemCount();

	List<HourlyBidActivityDto> getHourlyBidActivity();
}
