package project.pickme.item.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import project.pickme.item.constant.Status;
import project.pickme.user.domain.Customs;

import project.pickme.s3.service.S3Service;

import project.pickme.bid.repository.BidMapper;
import project.pickme.item.repository.ItemMapper;
import project.pickme.item.repository.LawMapper;
import project.pickme.item.repository.ItemLawMapper;
import project.pickme.delivery.repository.DeliveryMapper;

import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.LawDto;
import project.pickme.item.dto.ItemLawDto;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final LawMapper lawMapper;
	private final ItemMapper itemMapper;
	private final BidMapper bidMapper;
	private final ItemLawMapper itemLawMapper;
	private final DeliveryMapper deliveryMapper;

	private final S3Service s3Service;

	public void save(ItemFormDto itemFormDto, Customs customs) throws IOException {

		ItemDto itemDto = new ItemDto(itemFormDto.getName(), itemFormDto.getCode(), itemFormDto.getType(),
			itemFormDto.getPrice(), itemFormDto.getStartTime(), itemFormDto.getEndTime(),
			Status.NOT_OPEN, customs.getId());

		itemMapper.insertItem(itemDto);

		for (long id : itemFormDto.getLawId()) {
			ItemLawDto itemLawDto = new ItemLawDto(id, itemDto.getItemId());
			itemLawMapper.insertLaw(itemLawDto);
		}
		s3Service.uploadImages(itemDto, itemFormDto.getFiles());
	}

	public List<LawDto> findAllLaws() {
		return lawMapper.findAllLaws();
	}

	public List<ItemDto> findItemsByCustomsId(String customsId) {
		List<ItemDto> itemList = itemMapper.findItemsByCustomsId(customsId);
		List<Long> succesfulBidItemIdList = bidMapper.successfulBidItemId();
		List<Long> deliveryRegisterdItemIdList = deliveryMapper.registeredInvoiceItemId();
		for (ItemDto item : itemList) {
			Long itemId = item.getItemId();
			if (succesfulBidItemIdList.contains(itemId) && deliveryRegisterdItemIdList.contains(itemId)) {
				item.setIsSuccess(Boolean.TRUE);
				item.setIsRegisteredInvoiceNum(Boolean.TRUE);
			} else if (succesfulBidItemIdList.contains(itemId)) {
				item.setIsSuccess(Boolean.TRUE);
				item.setIsRegisteredInvoiceNum(Boolean.FALSE);
			} else {
				item.setIsSuccess(Boolean.FALSE);
			}
		}
		return itemList;
	}
}
