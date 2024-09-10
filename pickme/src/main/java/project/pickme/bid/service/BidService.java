package project.pickme.bid.service;

import static project.pickme.common.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.BidCreateDto;
import project.pickme.bid.dto.MaxPriceDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.bid.webSocket.WebSocketService;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.bid.dto.OneBidItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final BidMapper bidMapper;
	private final ItemMapper itemMapper;
	private final WebSocketService webSocketService;

	@Transactional
	public void addBid(User user, Long itemId, long price){
		Item item = itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
		BidCreateDto bid = BidCreateDto.create(price, user.getId(), item.getId());

		bidMapper.save(bid);
		//TODO: 현재 최고가를 실시간 전송 해야함
		MaxPriceDto data = new MaxPriceDto(price + 10000);
		webSocketService.sendBid2Client(user.getId(), data);
	}

	public OneBidItemDto showOneBidItem(User user, long itemId) {
		Item item = itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
		//TODO: 이미지 조회 로직

		return OneBidItemDto.of(item, user, "test.png");
	}
}
