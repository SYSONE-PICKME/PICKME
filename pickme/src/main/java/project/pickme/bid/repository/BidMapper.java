package project.pickme.bid.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.request.BidCreateDto;

@Mapper
public interface BidMapper {
	void save(BidCreateDto bid);

	List<Bid> findAll();

	void deleteAll();
}
