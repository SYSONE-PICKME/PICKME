package project.pickme.bid.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.dto.SuccessfulBidDto;

@Mapper
public interface BidMapper {
	void save(BidDto bid);

	List<Bid> findAll();

	void deleteAll();

	Long findMaxBidByItemId(Long itemId);

	List<Long> findAllPriceByItemId(Long itemId);

	Optional<Bid> findBidById(Long bidId);

	void updateBidSuccess(Long bidId);

	List<SuccessfulBidDto> findMySuccessfulBid(String id);
}
