package project.pickme.item.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.item.domain.Item;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.dto.OneBidItemDto;

@Mapper
public interface FindItemMapper {
	Optional<FindItemDto.GetOne> findById(@Param("id") Long id, @Param("userId") String currentUserId);

	List<FindItemDto.GetAll> findAll();

	Optional<Item> findItemById(Long itemId);

	OneBidItemDto findItemByIdWithImage(Long itemId);

	List<FindItemDto.GetAll> findAll(String userId);
}
