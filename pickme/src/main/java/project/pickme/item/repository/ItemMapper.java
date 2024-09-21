package project.pickme.item.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.OriginalItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;
import project.pickme.item.dto.UpdateDto;

@Mapper
public interface ItemMapper {
	void insertItem(ItemDto itemDto);

	void deleteAll();

	List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId);

	void updateStatus(LocalDateTime now);

	void updateItem(UpdateDto updateDto);

	OriginalItemDto findItemByItemId(long itemId);

	String[] findImagesByItemId(long itemId);
}
