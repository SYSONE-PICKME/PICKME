package project.pickme.image.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.image.dto.ImageDto;

@Mapper
public interface ImageMapper {
	void insertImage(ImageDto imageDto);

	void deleteAll();

	String[] findImagesByItemId(long itemId);
}
