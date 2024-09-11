package project.pickme.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.domain.ItemDTO;
import project.pickme.item.domain.ItemFormDTO;
import project.pickme.item.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public void delete(Long id) {
		itemRepository.delete(id);
	}

	public void save(ItemDTO item) {
		itemRepository.insertItem(item);
	}

	public void update(ItemDTO item) {
		itemRepository.updateItem(item);
	}

	public ItemDTO findById(Long id) {
		return itemRepository.findById(id);
	}


}
