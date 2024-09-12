package project.pickme.item.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemDto;

@Mapper
public interface ItemMapper {

	void insertItem(ItemDto item);

	void updateItem(ItemDto item);

	ItemDto findById(Long id);

	void delete(Long id);
}
