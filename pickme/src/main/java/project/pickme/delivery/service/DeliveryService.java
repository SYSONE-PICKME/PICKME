package project.pickme.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.delivery.repository.DeliveryMapper;
import project.pickme.item.dto.ItemDto;
import project.pickme.user.domain.Customs;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryMapper deliveryMapper;

	public List<ItemDto> getCustomsSuccessfulItems(Customs customs) {
		return deliveryMapper.findCustomsSuccessfulItems(customs.getId());
	}
}
