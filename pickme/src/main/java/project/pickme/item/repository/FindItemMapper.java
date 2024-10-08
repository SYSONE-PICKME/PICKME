package project.pickme.item.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import project.pickme.item.domain.Item;
import project.pickme.item.dto.FindItemDto;
import project.pickme.item.dto.ItemRequest;
import project.pickme.item.dto.ItemRequest.BidCursor;
import project.pickme.item.dto.OneBidItemDto;
import project.pickme.item.dto.OriginalItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;

@Mapper
public interface FindItemMapper {
	Optional<FindItemDto.GetOne> findById(@Param("id") Long id, @Param("userId") String currentUserId);

	List<FindItemDto.GetAll> findTop20();

	Optional<Item> findItemById(Long itemId);

	OneBidItemDto findItemByIdWithImage(Long itemId);

	List<FindItemDto.GetAll> findAll(
		@Param("userId") String userId,
		@Param("cursor") ItemRequest.Cursor cursor,
		@Param("size") int size
	);

	List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId);

	OriginalItemDto findByItemId(long itemId);

	List<FindItemDto.WishList> findWishList(@Param("userId") String userId, @Param("pageable") Pageable pageable);

	List<FindItemDto.MyBid> findBidList(
		@Param("userId") String userId,
		@Param("cursor") BidCursor cursor,
		@Param("size") int size
	);

	long countTotalMyWish(String userId);
}
