package project.pickme.charge.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.charge.dto.ChargeDto;

@Mapper
public interface ChargeMapper {
	void save(ChargeDto.Create charge);

	void deleteAll();
}
