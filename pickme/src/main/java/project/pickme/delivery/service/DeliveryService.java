package project.pickme.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.delivery.dto.CarrierDto;
import project.pickme.delivery.dto.DeliveryFormDto;
import project.pickme.delivery.dto.DeliveryInfoDto;
import project.pickme.delivery.dto.DeliveryManageDto;
import project.pickme.delivery.dto.ItemInfoDto;
import project.pickme.delivery.repository.DeliveryMapper;
import project.pickme.user.domain.Customs;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryMapper deliveryMapper;
	@Value("${tracker.api.clientId}")
	private String clientId;

	@Value("${tracker.api.clientSecret}")
	private String clientSecret;

	// 복호화된 값 사용
	public String getAuthHeader() {
		return "TRACKQL-API-KEY " + clientId + ":" + clientSecret;
	}

	public List<DeliveryManageDto> getCustomsSuccessfulItems(Customs customs) {
		return deliveryMapper.findCustomsSuccessfulItems(customs.getId());
	}

	public void saveDelivery(DeliveryFormDto deliveryFormDto) {
		deliveryMapper.saveDeliveryInfo(deliveryFormDto.getItemId(), deliveryFormDto.getUserId(),
			deliveryFormDto.getInvoiceNumber(), deliveryFormDto.getCode(), "배송 준비 중", deliveryFormDto.getCourier());
	}

	public ItemInfoDto getItemInfo(long itemId) {
		return deliveryMapper.getItemInfo(itemId);
	}

	public DeliveryInfoDto getDeliveryInfo(String userId) {
		return deliveryMapper.getDeliveryInfo(userId);
	}

	public CarrierDto getCarrier(long itemId) {
		return deliveryMapper.getTrackingInfo(itemId);
	}
}
