package project.pickme.payment.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.payment.dto.SavePaymentDto;

@Mapper
public interface PaymentMapper {
	void save(SavePaymentDto savePaymentDto);

	void deleteAll();

	long countTotalPayment(String id);
}
