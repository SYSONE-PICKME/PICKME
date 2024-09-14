package project.pickme.notice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.mapper.NoticeMapper;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;
	private final CustomsMapper customsMapper;

	public List<NoticeDto> getAllNotice() {
		return noticeMapper.selectAll().stream()
			.map(NoticeDto::fromEntity)
			.collect(Collectors.toList());
	}

	public NoticeDto getNoticeOrCampaignById(Long id) {
		Notice notice = noticeMapper.selectById(id);
		return convertToAppropriateDto(notice);
	}

	@Transactional
	public NoticeDto createNotice(NoticeDto noticeDto) {
		Optional<Customs> customsOptional = customsMapper.findById(noticeDto.getCustomsId());
		Customs customs = customsOptional.get();
		Notice notice = noticeDto.toEntity(customs);
		noticeMapper.insert(notice);
		return NoticeDto.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	@Transactional
	public void updateNotice(NoticeDto noticeDto) {
		Optional<Customs> customsOptional = customsMapper.findById(noticeDto.getCustomsId());
		Customs customs = customsOptional.get();
		Notice notice = noticeDto.toEntity(customs);
		noticeMapper.update(notice);
	}

	@Transactional
	public void deleteNotice(long id) {
		noticeMapper.delete(id);
	}

	@Transactional
	public CampaignDto createCampaign(CampaignDto campaignDto) {
		Optional<Customs> customsOptional = customsMapper.findById(campaignDto.getCustomsId());
		Customs customs = customsOptional.get();
		Notice notice = campaignDto.toEntity(customs);
		noticeMapper.insert(notice);
		return CampaignDto.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	private NoticeDto convertToAppropriateDto(Notice notice) {
		if (notice == null) {
			return null;
		}
		if (notice.getContent().contains("[Image URL: ")) {
			return CampaignDto.fromEntity(notice);
		}
		return NoticeDto.fromEntity(notice);
	}
}
