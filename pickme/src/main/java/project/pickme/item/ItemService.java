package project.pickme.item;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.mapper.FindItemMapper;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final FindItemMapper itemMapper;

	public ItemDto.Info findById(Long id) {
		ItemDto.GetOne item = itemMapper.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 공매품이 존재하지 않습니다."));

		return new ItemDto.Info(item);
	}
}
