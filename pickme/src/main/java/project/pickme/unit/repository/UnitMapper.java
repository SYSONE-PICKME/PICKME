package project.pickme.unit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.unit.domain.Unit;

@Mapper
public interface UnitMapper {
	List<Unit> findAll();
}
