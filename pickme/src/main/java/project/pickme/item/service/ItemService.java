package project.pickme.item.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import project.pickme.item.constant.Status;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.LawDto;
import project.pickme.item.mapper.ItemMapper;
import project.pickme.item.mapper.LawMapper;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final LawMapper lawMapper;
	private final ItemMapper itemMapper;

	public void save(ItemFormDto itemFormDto) {
		LocalDateTime start = LocalDateTime.of(itemFormDto.getStartDate(), itemFormDto.getStartTime());
		LocalDateTime end = LocalDateTime.of(itemFormDto.getEndDate(), itemFormDto.getEndTime());
		ItemDto itemDto = new ItemDto(itemFormDto.getName(), itemFormDto.getCode(), itemFormDto.getTarget(),
			itemFormDto.getPrice(), start, end,
			Status.NOT_OPEN, "gunsan");
		itemMapper.insertItem(itemDto);
	}

	public List<LawDto> findAllLaws() {
		return lawMapper.findAllLaws();
	}
}
