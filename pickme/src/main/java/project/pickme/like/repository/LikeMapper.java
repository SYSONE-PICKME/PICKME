package project.pickme.like.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.like.dto.LikeDto;

@Mapper
public interface LikeMapper {
	void save(@Param("itemId") Long itemId, @Param("userId") String userId);

	void updateStatus(@Param("itemId") Long itemId, @Param("userId") String userId);

	Optional<LikeDto> findByItemIdAndUserId(@Param("itemId") Long itemId, @Param("userId") String userId);
}
