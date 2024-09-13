package project.pickme.bid.repository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.BidCreateDto;

@Mapper
public interface BidMapper {
	void save(BidCreateDto bid);

	List<Bid> findAll();

	void deleteAll();

	Long findMaxBidByItemId(Long itemId);

	Optional<Bid> findBidById(Long bidId);

	void updateBidSuccess(Long bidId);
}
