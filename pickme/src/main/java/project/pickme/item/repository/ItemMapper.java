package project.pickme.item.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.domain.Item;
import project.pickme.item.dto.ItemDto;

@Mapper
public interface ItemMapper {
	Optional<Item> findItemById(Long itemId);

	void insertItem(ItemDto itemDto);

	List<ItemDto> findItemsByCustomsId(String customsId);
}
