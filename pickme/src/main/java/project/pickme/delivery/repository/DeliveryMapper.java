package project.pickme.delivery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.delivery.dto.DeliveryInfoDto;
import project.pickme.delivery.dto.DeliveryManageDto;
import project.pickme.delivery.dto.ItemInfoDto;

@Mapper
public interface DeliveryMapper {
	List<DeliveryManageDto> findCustomsSuccessfulItems(String id);

	void saveDeliveryInfo(long itemId, String userId, String invoiceNumber, String code, String status, String courier);

	DeliveryInfoDto getDeliveryInfo(String userId);
  
	ItemInfoDto getDeliveryItemInfo(long itemId);

}
