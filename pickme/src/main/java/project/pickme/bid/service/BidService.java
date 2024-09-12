package project.pickme.bid.service;

import static project.pickme.common.exception.ErrorCode.*;
import static project.pickme.item.constant.Status.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.AddBidDto;
import project.pickme.bid.dto.BidCreateDto;
import project.pickme.bid.dto.MaxPriceDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.bid.dto.OneBidItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final ItemMapper itemMapper;
	private final UserMapper userMapper;
	private final BidMapper bidMapper;

	public OneBidItemDto showOneBidItem(User user, Long itemId) {
		Item item = getItem(itemId);
		Long maxPrice = bidMapper.findMaxBidByItemId(item.getId());

		//TODO: 이미지 조회 로직
		return OneBidItemDto.of(item, user, maxPrice, "test.png");
	}

	@Transactional
	public MaxPriceDto addBid(AddBidDto addBidDto){	//입찰하는 메서드
		User user = userMapper.findUserById(addBidDto.getUserId()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
		Item item = getItem(addBidDto.getItemId());

		if(item.getStatus().equals(OPEN)){
			BidCreateDto bidCreateDto = BidCreateDto.create(addBidDto.getPrice(), user.getId(), item.getId());
			bidMapper.save(bidCreateDto);

			return MaxPriceDto.create(bidCreateDto.getBidId(), addBidDto.getPrice());
		}

		throw new BusinessException(BID_NOT_PROGRESS);
	}

	@Transactional
	public void closeBid(Long bidId){
		Bid bid = bidMapper.findBidById(bidId).orElseThrow(() -> new BusinessException(NOT_FOUND_BID));

		bidMapper.updateBidSuccess(bidId);
		userMapper.minusPoint(bid.getUser().getId(), bid.getPrice());	//포인트 차감
	}

	private Item getItem(Long itemId) {
		return itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
	}
}
