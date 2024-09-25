package project.pickme.item.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.item.dto.ItemLawDto;

@Mapper
public interface ItemLawMapper {
	void bulkInsert(@Param("laws") List<ItemLawDto> laws);

	void delete(long itemId);

	List<Long> findByItemId(long itemId);
}
