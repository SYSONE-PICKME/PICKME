package project.pickme.item.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.item.dto.FindItemDto;

@Mapper
public interface FindItemMapper {
	Optional<FindItemDto.GetOne> findById(@Param("id") Long id, @Param("userId") String currentUserId);
}
