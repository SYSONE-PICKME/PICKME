package project.pickme.delivery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.delivery.dto.DeliveryManageDto;

@Mapper
public interface DeliveryMapper {
	List<DeliveryManageDto> findCustomsSuccessfulItems(String id);

	// TODO: 다시 해야함
	void saveDeliveryInfo(long itemId, String userId, String invoiceNumber, String code, String status);
}
