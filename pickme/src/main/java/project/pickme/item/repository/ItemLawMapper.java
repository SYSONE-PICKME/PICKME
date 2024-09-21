package project.pickme.item.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemLawDto;

@Mapper
public interface ItemLawMapper {
	void insertLaw(ItemLawDto itemLawDto);

	void deleteLaw(long itemId);

	List<Long> findLawsByItemId(long itemId);
}
