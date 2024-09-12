package project.pickme.item.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.repository.ItemMapper;

@Service
@RequiredArgsConstructor
public class ItemService {


	private final ItemMapper itemMapper;

	public void delete(Long id) {
		itemMapper.delete(id);
	}

	public void save(ItemDto item) {
		itemMapper.insertItem(item);
	}

	public void update(ItemDto item) {
		itemMapper.updateItem(item);
	}

	public ItemDto findById(Long id) {
		return itemMapper.findById(id);
	}


}
