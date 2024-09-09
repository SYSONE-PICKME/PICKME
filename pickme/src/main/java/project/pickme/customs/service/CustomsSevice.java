package project.pickme.customs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.customs.domain.Customs;
import project.pickme.customs.repository.CustomsRepository;

@Service
@RequiredArgsConstructor
public class CustomsSevice implements CustomsRepository {

	@Autowired
	private final CustomsRepository customsRepository;



	@Override
	public void save(Customs customs) {
		customsRepository.save(customs);
	}

	@Override
	public Customs findById(String id) {
		return customsRepository.findById(id);
	}

	@Override
	public void deleteAll(){
		customsRepository.deleteAll();
	}
}
