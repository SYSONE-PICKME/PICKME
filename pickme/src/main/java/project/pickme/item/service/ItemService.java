package project.pickme.item.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import project.pickme.image.repository.ImageMapper;
import project.pickme.item.constant.Status;
import project.pickme.item.dto.OriginalItemDto;
import project.pickme.item.dto.SuccessfullCustomsItemDto;
import project.pickme.item.dto.UpdateDto;
import project.pickme.item.dto.UpdateItemFormDto;
import project.pickme.item.repository.FindItemMapper;
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
	private final ImageMapper imageMapper;
	private final FindItemMapper findItemMapper;

	private final S3Service s3Service;

	public void save(ItemFormDto itemFormDto, Customs customs) throws IOException {

		ItemDto itemDto = new ItemDto(itemFormDto.getName(), itemFormDto.getCode(), itemFormDto.getType(),
			itemFormDto.getPrice(), itemFormDto.getStartTime(), itemFormDto.getEndTime(),
			Status.NOT_OPEN, customs.getId());

		itemMapper.insert(itemDto);
		System.out.println("----");
		List<ItemLawDto> laws = new ArrayList<>();
		for (long lawId : itemFormDto.getLawId()) {
			laws.add(new ItemLawDto(lawId,itemDto.getItemId())); // ItemLawDto 리스트 생성
		}
		itemLawMapper.bulkInsert(laws);
		System.out.println("==");

		s3Service.uploadImages(itemDto, itemFormDto.getFiles());
	}

	public List<LawDto> findAllLaws() {
		return lawMapper.findAll();
	}

	// 등록한 경매 전체 조회 및 낙찰된 물품은 마감처리
	public List<SuccessfullCustomsItemDto> findItemsByCustomsId(String customsId) {
		return findItemMapper.findItemsByCustomsId(customsId);
	}

	public List<Long> getLawsByItemId(long itemId) {
		return itemLawMapper.findByItemId(itemId);
	}

	public String[] getImagesByItemId(long itemId) {
		return imageMapper.findImagesByItemId(itemId);
	}

	public OriginalItemDto getItemById(long itemId) {
		return findItemMapper.findByItemId(itemId);
	}

	public void updateItemByItemId(Customs customs, UpdateItemFormDto updateItemFormDto, long itemId) {
		if (customs.getId().equals(findItemMapper.findByItemId(itemId).getCustomsId())) {
			updateItemLaws(itemId, updateItemFormDto.getLawId());
			updateItemDetails(updateItemFormDto, itemId);
		}
	}

	private void updateItemLaws(long itemId, long[] lawIds) {
		itemLawMapper.delete(itemId);
		List<ItemLawDto> laws=new ArrayList<>();
		for (long id : lawIds) {
			laws.add(new ItemLawDto(id, itemId));
		}
		itemLawMapper.bulkInsert(laws);
	}

	private void updateItemDetails(UpdateItemFormDto updateItemFormDto, long itemId) {
		itemMapper.update(new UpdateDto(updateItemFormDto.getStartTime(), updateItemFormDto.getEndTime(), itemId));
	}
}
