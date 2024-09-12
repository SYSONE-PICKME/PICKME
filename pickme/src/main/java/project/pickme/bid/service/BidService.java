package project.pickme.bid.service;

import static project.pickme.common.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.bid.dto.OneBidItemDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
	private final ItemMapper itemMapper;

	public OneBidItemDto showOneBidItem(User user, long itemId) {
		Item item = itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));

		//TODO: 이미지 조회 로직
		return OneBidItemDto.of(item, user, "test.png");
	}
}
