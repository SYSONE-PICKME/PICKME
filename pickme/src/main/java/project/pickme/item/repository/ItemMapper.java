package project.pickme.item.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;

@Mapper
public interface ItemMapper {
	void insertItem(ItemDto itemDto);

	void deleteAll();

	List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId);

	void updateStatus(LocalDateTime now);
}
