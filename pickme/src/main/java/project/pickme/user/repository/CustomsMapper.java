package project.pickme.user.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.user.domain.Customs;

@Mapper
public interface CustomsMapper {
	List<Customs> findAll();
	Optional<Customs> findById(String id);
	void saveAll(List<Customs> customs);
	void deleteAll();
}
