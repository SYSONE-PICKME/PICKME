package project.pickme.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.mapper.NoticeMapper;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapper noticeMapper;

	@Transactional(readOnly = true)
	public List<NoticeDto> getAllNotice() {
		return noticeMapper.selectAll().stream()
			.map(NoticeDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public NoticeDto getNoticeById(long id) {
		Notice notice = noticeMapper.selectById(id);
		return notice != null ? NoticeDto.fromEntity(notice) : null;
	}

	@Transactional
	public NoticeDto createNotice(NoticeDto noticeDto) {
		Notice notice = noticeDto.toEntity();
		noticeMapper.insert(notice);
		return NoticeDto.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	@Transactional
	public void updateNotice(NoticeDto noticeDto) {
		Notice notice = noticeDto.toEntity();
		noticeMapper.update(notice);
	}

	@Transactional
	public void deleteNotice(long id) {
		noticeMapper.delete(id);
	}

	@Transactional
	public CampaignDto createCampaign(CampaignDto campaignDto) {
		Notice notice = campaignDto.toEntity();
		noticeMapper.insert(notice);
		return CampaignDto.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	@Transactional
	public CampaignDto getCampaignById(long id) {
		Notice notice = noticeMapper.selectById(id);
		return notice != null ? CampaignDto.fromEntity(notice) : null;
	}
}
