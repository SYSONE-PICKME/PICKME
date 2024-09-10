package project.pickme.item.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.item.domain.Law;
import project.pickme.item.domain.LawDTO;

@Mapper
public interface LawRepository {
	List<LawDTO> findAllLaws();
}
