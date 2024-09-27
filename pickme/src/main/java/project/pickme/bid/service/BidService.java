package project.pickme.bid.service;

import static project.pickme.bid.exception.BidErrorCode.*;
import static project.pickme.item.exception.ItemErrorCode.*;
import static project.pickme.user.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.bid.dto.reqeust.AddBidDto;

import project.pickme.bid.dto.response.UnPaidBidDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.bid.dto.MySuccessfulBidDto;

import project.pickme.bid.dto.response.BidDto;
import project.pickme.bid.dto.response.BidDetailsDto;
import project.pickme.bid.dto.response.PriceDto;
import project.pickme.bid.dto.response.UpdatePriceBidDto;
import project.pickme.item.repository.FindItemMapper;

import project.pickme.payment.repository.PaymentMapper;
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

	@Transactional
	public UpdatePriceBidDto addBid(AddBidDto addBidDto) {    //입찰하는 메서드
		Item item = itemMapper.findItemById(addBidDto.getItemId())
			.orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));

		if (item.isOpen()) {
			User user = userMapper.findUserById(addBidDto.getUserId()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
			BidDto bidDto = BidDto.create(addBidDto.getPrice(), user.getId(), item.getId());
			bidMapper.save(bidDto);

			return UpdatePriceBidDto.createOf(bidDto.getBidId(), addBidDto.getPrice(), addBidDto.getUserId());
		}

		throw new BusinessException(BID_NOT_PROGRESS);
	}

	public BidDetailsDto showBidDetails(Long itemId, User user) {
		User findUser = userMapper.findUserById(user.getId()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
		List<PriceDto> allPrices = bidMapper.findAllPriceByItemId(itemId);

		return BidDetailsDto.createOf(allPrices, findUser.getPoint());
	}

	public Page<MySuccessfulBidDto> findMySuccessfulBid(User user, Pageable pageable) {
		List<MySuccessfulBidDto> mySuccessfulBids = bidMapper.findMySuccessfulBid(user.getId(), pageable);
		long totalCount = paymentMapper.countTotalPayment(user.getId());

		return new PageImpl<>(mySuccessfulBids, pageable, totalCount);
	}

	public Page<UnPaidBidDto> findMyUnpaidBid(String id, Pageable pageable) {
		List<UnPaidBidDto> unPaidBid = bidMapper.findUnPaidBid(id, pageable);
		long totalCount = bidMapper.countTotalUnpaidBid(id);

		return new PageImpl<>(unPaidBid, pageable, totalCount);
	}
}
