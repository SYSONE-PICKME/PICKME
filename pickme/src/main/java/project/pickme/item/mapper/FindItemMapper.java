package project.pickme.item.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemDto;

@Mapper
public interface FindItemMapper {
	Optional<ItemDto.GetOne> findById(Long id);
}
