package project.pickme.item.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.pickme.item.domain.LawDTO;
import project.pickme.item.repository.LawRepository;

@Service
public class LawService {
	@Autowired
	private LawRepository lawRepository;

	public List<LawDTO> getLaws() {
		List<LawDTO> laws = lawRepository.findAllLaws();
		return laws;
	}
}
