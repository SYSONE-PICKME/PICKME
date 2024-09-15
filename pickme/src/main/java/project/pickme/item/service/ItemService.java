package project.pickme.item.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.item.constant.Status;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.LawDto;
import project.pickme.item.dto.ItemLawDto;
import project.pickme.item.repository.ItemMapper;
import project.pickme.item.repository.LawMapper;
import project.pickme.item.repository.ItemLawMapper;
import project.pickme.s3.service.S3Service;
import project.pickme.user.domain.Customs;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
	private final LawMapper lawMapper;
	private final ItemMapper itemMapper;
	private final ItemLawMapper itemLawMapper;

	private final S3Service s3Service;

	public void save(ItemFormDto itemFormDto, MultipartFile[] files, Customs customs) throws IOException {
		LocalDateTime start = LocalDateTime.of(itemFormDto.getStartDate(), itemFormDto.getStartTime());
		LocalDateTime end = LocalDateTime.of(itemFormDto.getEndDate(), itemFormDto.getEndTime());

		ItemDto itemDto = new ItemDto(itemFormDto.getName(), itemFormDto.getCode(), itemFormDto.getType(),
			itemFormDto.getPrice(), start, end,
			Status.NOT_OPEN, customs.getId());

		itemMapper.insertItem(itemDto);

		for (long id : itemFormDto.getLawId()) {
			ItemLawDto itemLawDto = new ItemLawDto(id, itemDto.getItemId());
			itemLawMapper.insertLaw(itemLawDto);
		}
		s3Service.uploadImages(itemDto, files);
	}

	public List<LawDto> findAllLaws() {
		return lawMapper.findAllLaws();
	}
}
