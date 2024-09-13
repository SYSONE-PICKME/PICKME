package project.pickme.item.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ImageDto;

@Mapper
public interface ImageMapper {
	void insertImage(ImageDto imageDto);
}
