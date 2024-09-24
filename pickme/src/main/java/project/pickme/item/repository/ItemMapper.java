package project.pickme.item.repository;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.UpdateDto;

@Mapper
public interface ItemMapper {
	void insert(ItemDto itemDto);

	void deleteAll();

	void updateStatus(LocalDateTime now);

	void update(UpdateDto updateDto);
}
