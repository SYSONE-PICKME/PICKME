package project.pickme.bid.service;

import static project.pickme.common.exception.ErrorCode.*;
import static project.pickme.item.constant.Status.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.reqeust.AddBidDto;
import project.pickme.bid.dto.response.BidCreateDto;
import project.pickme.bid.dto.response.MaxPriceDto;
import project.pickme.bid.dto.response.SelectedBidDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.bid.dto.response.OneBidItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.controller.MailService;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final ItemMapper itemMapper;
	private final UserMapper userMapper;
	private final BidMapper bidMapper;
	private final MailService mailService;

	public OneBidItemDto showOneBidItem(User user, Long itemId) {
		Item item = getItem(itemId);
		List<Long> allPrice = bidMapper.findAllPriceByItemId(item.getId());

		//TODO: 이미지 조회 로직
		return OneBidItemDto.of(item, user, allPrice, "test.png");
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
	public void selectBid(Long bidId, SelectedBidDto selectedBidDto) throws MessagingException {
		Bid bid = bidMapper.findBidById(bidId).orElseThrow(() -> new BusinessException(NOT_FOUND_BID));

		bidMapper.updateBidSuccess(bidId);
		userMapper.minusPoint(bid.getUserId(), bid.getPrice());	//포인트 차감

		//낙찰자에게 메일 전송
		mailService.sendSuccessfulBidMail(selectedBidDto, bid.getUserEmail());
	}

	private Item getItem(Long itemId) {
		return itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));
	}
}
