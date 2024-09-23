package project.pickme.item.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.OneBidItemDto;
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

	public List<FindItemDto.GetAll> findAll(String userId, String category) {
		return itemMapper.findAll(userId, category);
	}

	public List<FindItemDto.GetAll> findTop20() {
		return itemMapper.findTop20();
	}

	public OneBidItemDto showOneBidItem(User user, Long itemId) {
		OneBidItemDto onBidItemDto = itemMapper.findItemByIdWithImage(itemId);
		onBidItemDto.setUserId(user.getId());

		return onBidItemDto;
	}
}
