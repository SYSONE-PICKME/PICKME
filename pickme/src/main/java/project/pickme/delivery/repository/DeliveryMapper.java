package project.pickme.delivery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.dto.ItemDto;

@Mapper
public interface DeliveryMapper {
	List<ItemDto> findCustomsSuccessfulItems(String id);
}
