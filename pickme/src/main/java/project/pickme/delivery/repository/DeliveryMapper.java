package project.pickme.delivery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {
	List<Long> getRegisteredInvoiceItemId();
}
