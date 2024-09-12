package project.pickme.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.LawDto;

@Mapper
public interface LawMapper {
	List<LawDto> findAllLaws();
}
