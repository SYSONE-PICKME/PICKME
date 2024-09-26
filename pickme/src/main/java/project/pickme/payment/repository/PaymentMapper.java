package project.pickme.payment.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PaymentMapper {
	void save(@Param("bidId") Long bidId, @Param("id") String id);

	void deleteAll();

	long countTotalPayment(String id);
}
