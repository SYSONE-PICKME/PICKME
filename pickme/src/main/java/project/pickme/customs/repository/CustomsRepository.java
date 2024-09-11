package project.pickme.customs.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import project.pickme.customs.domain.Customs;

@Mapper
public interface CustomsRepository {
	void save(Customs customs);

	Customs findById(String id);

	void deleteAll();
}
