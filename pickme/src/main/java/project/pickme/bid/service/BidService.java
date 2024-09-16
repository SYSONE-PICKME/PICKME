package project.pickme.bid.service;

import static project.pickme.bid.exception.BidErrorCode.*;
import static project.pickme.item.exception.ItemErrorCode.*;
import static project.pickme.user.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import project.pickme.bid.domain.Bid;
import project.pickme.bid.dto.reqeust.AddBidDto;
import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.dto.response.BidDetailsDto;
import project.pickme.bid.dto.response.MaxPriceDto;
import project.pickme.bid.dto.response.SelectedBidDto;
import project.pickme.bid.dto.SuccessfulBidDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.item.repository.FindItemMapper;
import project.pickme.payment.dto.SavePaymentDto;
import project.pickme.payment.repository.PaymentMapper;
import project.pickme.user.service.MailService;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final FindItemMapper itemMapper;
	private final UserMapper userMapper;
	private final BidMapper bidMapper;
	private final PaymentMapper paymentMapper;
	private final MailService mailService;

	@Transactional
	public MaxPriceDto addBid(AddBidDto addBidDto) {    //입찰하는 메서드
		Item item = itemMapper.findItemById(addBidDto.getItemId()).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));

		if (item.isOpen()) {
			User user = userMapper.findUserById(addBidDto.getUserId()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
			BidDto bidDto = BidDto.create(addBidDto.getPrice(), user.getId(), item.getId());
			bidMapper.save(bidDto);

			return MaxPriceDto.createOf(bidDto.getBidId(), addBidDto.getPrice(), addBidDto.getUserId());
		}

		throw new BusinessException(BID_NOT_PROGRESS);
	}

	@Transactional
	public void selectBid(SelectedBidDto selectedBidDto) throws MessagingException {
		Bid bid = bidMapper.findBidById(selectedBidDto.getBidId()).orElseThrow(() -> new BusinessException(NOT_FOUND_BID));

		bidMapper.updateBidSuccess(selectedBidDto.getBidId());
		userMapper.minusPoint(bid.getUserId(), bid.getPrice());
		paymentMapper.save(SavePaymentDto.createOf(selectedBidDto.getUserId(), selectedBidDto.getBidId()));

		//낙찰자에게 메일 전송
		mailService.sendSuccessfulBidMail(selectedBidDto, bid.getUserEmail());
	}

	public BidDetailsDto showBidDetails(Long itemId, User user) {
		List<Long> allPrice = bidMapper.findAllPriceByItemId(itemId);

		return BidDetailsDto.createOf(allPrice, user.getPoint());
	}

	public List<SuccessfulBidDto> findMySuccessfulBid(User user) {
		return bidMapper.findMySuccessfulBid(user.getId());
	}
}
