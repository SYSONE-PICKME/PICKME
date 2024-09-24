package project.pickme.user.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import project.pickme.user.domain.Customs;
import project.pickme.user.dto.customs.IncomeDto;

@Mapper
public interface CustomsMapper {
	List<Customs> findAll();

	Optional<Customs> findByCustomsId(String id);

	void saveAll(List<Customs> customs);

	void deleteAll();

	void save(Customs customs);

	List<IncomeDto> findIncomeItemById(@Param("id") String id, @Param("pageable") Pageable pageable);

	long countTotalIncome(String id);
}
