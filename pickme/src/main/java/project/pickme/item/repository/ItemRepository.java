package project.pickme.item.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.domain.ItemDTO;

@Mapper
public interface ItemRepository {

	void insertItem(ItemDTO item);

	void updateItem(ItemDTO item);

	ItemDTO findById(Long id);

	void delete(Long id);
}
