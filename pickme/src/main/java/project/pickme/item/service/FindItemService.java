package project.pickme.item.service;

import static project.pickme.item.exception.ItemErrorCode.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.common.exception.BusinessException;
import project.pickme.item.domain.Item;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.repository.FindItemMapper;
import project.pickme.user.domain.User;

@Service
@RequiredArgsConstructor
public class FindItemService {
	private final FindItemMapper itemMapper;

	public FindItemDto.Info findById(Long id, String userId) {
		FindItemDto.GetOne item = itemMapper.findById(id, userId)
			.orElseThrow(() -> new NoSuchElementException("해당 공매품이 존재하지 않습니다."));

		return new FindItemDto.Info(item);
	}

	public List<FindItemDto.GetAll> findAll() {
		return itemMapper.findAll();
	}

	public OneBidItemDto showOneBidItem(User user, Long itemId) {
		Item item = itemMapper.findItemById(itemId).orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM));

		//TODO: 이미지 조회 로직
		return OneBidItemDto.createOf(item, user,"test.png");
	}
}
