package project.pickme.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.item.dto.LawDto;
import project.pickme.item.repository.LawMapper;

@Service
@RequiredArgsConstructor
public class LawService {

	private final LawMapper lawMapper;

	public List<LawDto> getLaws() {
		List<LawDto> laws = lawMapper.findAllLaws();
		return laws;
	}
}
