package project.pickme.item.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.domain.Item;

@Mapper
public interface ItemMapper {
	Optional<Item> findItemById(Long itemId);
}
