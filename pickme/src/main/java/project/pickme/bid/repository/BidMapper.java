package project.pickme.bid.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import project.pickme.bid.domain.Bid;

import project.pickme.bid.dto.MySuccessfulBidDto;

import project.pickme.bid.dto.reqeust.SelectedBidDto;
import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.dto.response.PriceDto;

@Mapper
public interface BidMapper {
	void save(BidDto bid);

	List<Bid> findAll();

	void deleteAll();

	Long findMaxBidByItemId(Long itemId);

	List<PriceDto> findAllPriceByItemId(Long itemId);

	Optional<Bid> findBidById(Long bidId);

	void updateBidSuccess(Long bidId);

	List<MySuccessfulBidDto> findMySuccessfulBid(@Param("id") String id, @Param("pageable") Pageable pageable);

	List<SelectedBidDto> findAllSuccessBid();
}
