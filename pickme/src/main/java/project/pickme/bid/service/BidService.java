package project.pickme.bid.service;

import static project.pickme.common.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
		Item item = itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
		Long maxPrice = bidMapper.findMaxBidByItemId(item.getId());

		//TODO: 이미지 조회 로직
		return OneBidItemDto.of(item, user, maxPrice, "test.png");
	}

	@Transactional
	public MaxPriceDto addBid(AddBidDto addBidDto){
		User user = userMapper.findUserById(addBidDto.getUserId()).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
		Item item = itemMapper.findItemById(addBidDto.getItemId()).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));

		Long savedBidId = bidMapper.save(BidCreateDto.create(addBidDto.getPrice(), user.getId(), item.getId()));
		return MaxPriceDto.create(savedBidId, addBidDto.getPrice());
	}
}
