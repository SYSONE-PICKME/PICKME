package project.pickme.item.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import project.pickme.item.dto.ItemDto;

@Mapper
public interface ItemMapper {
	void insertItem(ItemDto itemDto);

	void deleteAll();

	List<ItemDto> findItemsByCustomsId(String customsId);
}
