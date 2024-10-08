package project.pickme.item.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.ItemRequest;
import project.pickme.item.dto.ItemRequest.BidCursor;
import project.pickme.item.dto.LawDto;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.repository.FindItemMapper;
import project.pickme.item.repository.LawMapper;
import project.pickme.user.domain.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindItemService {
	private static final int DEFAULT_PAGE_SIZE = 16;

	private final FindItemMapper itemMapper;
	private final LawMapper lawMapper;

	public FindItemDto.Info findById(Long id, String userId) {
		FindItemDto.GetOne item = itemMapper.findById(id, userId)
			.orElseThrow(() -> new NoSuchElementException("해당 공매품이 존재하지 않습니다."));

		List<LawDto> laws = lawMapper.findByItemId(id);
		return new FindItemDto.Info(item, laws);
	}

	public List<FindItemDto.GetAll> findAll(String userId, ItemRequest.Cursor cursor) {
		return itemMapper.findAll(userId, cursor, DEFAULT_PAGE_SIZE);
	}

	public List<FindItemDto.GetAll> findTop20() {
		return itemMapper.findTop20();
	}

	public OneBidItemDto showOneBidItem(User user, Long itemId) {
		OneBidItemDto onBidItemDto = itemMapper.findItemByIdWithImage(itemId);
		onBidItemDto.setUserId(user.getId());

		return onBidItemDto;
	}

	public Page<FindItemDto.WishList> findWishList(String userId, Pageable pageable) {
		List<FindItemDto.WishList> wishList = itemMapper.findWishList(userId, pageable);
		long totalCount = itemMapper.countTotalMyWish(userId);

		return new PageImpl<>(wishList, pageable, totalCount);
	}

	public List<FindItemDto.MyBid> findBidList(String userId, BidCursor cursor) {
		return itemMapper.findBidList(userId, cursor, DEFAULT_PAGE_SIZE);
	}
}
