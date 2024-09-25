package project.pickme.item.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.item.domain.Item;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.item.dto.OriginalItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;

@Mapper
public interface FindItemMapper {
	Optional<FindItemDto.GetOne> findById(@Param("id") Long id, @Param("userId") String currentUserId);

	List<FindItemDto.GetAll> findTop20();

	Optional<Item> findItemById(Long itemId);

	OneBidItemDto findItemByIdWithImage(Long itemId);

	List<FindItemDto.GetAll> findAll(@Param("userId") String userId, @Param("category") String category);

	List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId);

	OriginalItemDto findByItemId(long itemId);

	List<FindItemDto.WishList> findWishList(String userId);
}
