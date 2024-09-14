package project.pickme.item.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.LawDto;

@Mapper
public interface LawMapper {
	List<LawDto> findAllLaws();
}
