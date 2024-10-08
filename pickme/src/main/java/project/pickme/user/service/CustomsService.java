package project.pickme.user.service;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import project.pickme.user.domain.Customs;
import project.pickme.user.dto.customs.IncomeDto;
import project.pickme.user.repository.CustomsMapper;

@Service
@RequiredArgsConstructor
// @Profile("!test")
public class CustomsService {
	private final BCryptPasswordEncoder passwordEncoder;
	private final CustomsMapper customsMapper;
  
  	// @PostConstruct
	// public void initCustomsData() {
	// 	if(customsMapper.findAll().isEmpty()){
	// 		List<Customs> customs = List.of(
	// 			Customs.createCustoms("gunsan", passwordEncoder.encode("gunsan"), "군산세관", "064-730-8710"),
	// 			Customs.createCustoms("kimpo", passwordEncoder.encode("kimpo"), "김포공항세관", "064-730-8710"),
	// 			Customs.createCustoms("incheon", passwordEncoder.encode("incheon"), "인천세관", "064-730-8710"),
	// 			Customs.createCustoms("changwon", passwordEncoder.encode("changwon"), "창원세관", "064-730-8710")
	// 		);
	//
	// 		customsMapper.saveAll(customs);
	// 	}
	// }

	public Page<IncomeDto> findIncome(String customsId, Pageable pageable) {
		List<IncomeDto> incomes = customsMapper.findIncomeItemById(customsId, pageable);
		long totalCount = customsMapper.countTotalIncome(customsId);

		return new PageImpl<>(incomes, pageable, totalCount);
	}
}
