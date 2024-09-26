package project.pickme.image.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.image.dto.ImageDto;

@Mapper
public interface ImageMapper {
	void insertImages(@Param("images") List<ImageDto> images);

	void deleteAll();

	String[] findImagesByItemId(long itemId);
}
