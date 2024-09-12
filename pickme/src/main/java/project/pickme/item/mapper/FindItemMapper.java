package project.pickme.item.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.FindItemDto;

@Mapper
public interface FindItemMapper {
	Optional<FindItemDto.GetOne> findById(Long id);

	List<FindItemDto.GetAll> findAll();
}
