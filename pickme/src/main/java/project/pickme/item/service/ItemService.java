package project.pickme.item.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import project.pickme.item.constant.Status;
import project.pickme.item.dto.OriginalItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;
import project.pickme.item.dto.UpdateDto;
import project.pickme.item.dto.UpdateItemFormDto;
import project.pickme.user.domain.Customs;

import project.pickme.s3.service.S3Service;

import project.pickme.item.repository.ItemMapper;
import project.pickme.item.repository.LawMapper;
import project.pickme.item.repository.ItemLawMapper;

import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.LawDto;
import project.pickme.item.dto.ItemLawDto;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final LawMapper lawMapper;
	private final ItemMapper itemMapper;
	private final ItemLawMapper itemLawMapper;

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

	// 등록한 경매 전체 조회 및 낙찰된 물품은 마감처리
	public List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId) {
		return itemMapper.findItemsByCustomsId(customsId);
	}

	public List<Long> getLawsByItemId(long itemId) {
		return itemLawMapper.findLawsByItemId(itemId);
	}

	public String[] getImagesByItemId(long itemId) {
		return itemMapper.findImagesByItemId(itemId);
	}

	public OriginalItemDto getItemById(long itemId) {
		return itemMapper.findItemByItemId(itemId);
	}

	public void updateItemByItemId(Customs customs, UpdateItemFormDto updateItemFormDto, long itemId) {
		if (customs.getId().equals(itemMapper.findItemByItemId(itemId).getCustomsId())) {
			itemLawMapper.deleteLaw(itemId);
			for (long id : updateItemFormDto.getLawId()) {
				itemLawMapper.insertLaw(new ItemLawDto(id, itemId));
			}
			itemMapper.updateItem(
				new UpdateDto(updateItemFormDto.getStartTime(), updateItemFormDto.getEndTime(), itemId));
		}
	}
}
