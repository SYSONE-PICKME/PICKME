package project.pickme.item.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemLawDto;

@Mapper
public interface ItemLawMapper {
	void insertLaw(ItemLawDto itemLawDto);
}
