package project.pickme.image.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.image.dto.ImageDto;

@Mapper
public interface ImageMapper {
	void insertImages(List<ImageDto> images);

	void deleteAll();

	String[] findImagesByItemId(long itemId);
}
