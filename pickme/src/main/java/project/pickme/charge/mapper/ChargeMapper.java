package project.pickme.charge.mapper;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.charge.dto.ChargeDto;

@Mapper
public interface ChargeMapper {
	void save(ChargeDto.Create charge);
}
