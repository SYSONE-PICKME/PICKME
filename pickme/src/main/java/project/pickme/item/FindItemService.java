package project.pickme.item;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.repository.FindItemMapper;

@Service
@RequiredArgsConstructor
public class FindItemService {
	private final FindItemMapper itemMapper;

	public FindItemDto.Info findById(Long id, String userId) {
		FindItemDto.GetOne item = itemMapper.findById(id, userId)
			.orElseThrow(() -> new NoSuchElementException("해당 공매품이 존재하지 않습니다."));

		return new FindItemDto.Info(item);
	}

	public List<FindItemDto.GetAll> findAll(String userId) {
		return itemMapper.findAll(userId);
	}
}
