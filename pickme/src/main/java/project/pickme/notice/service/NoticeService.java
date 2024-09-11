package project.pickme.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDTO;
import project.pickme.notice.dto.NoticeDTO;
import project.pickme.notice.mapper.NoticeMapper;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	public List<NoticeDTO> getAllNotice() {
		return noticeMapper.selectAll().stream()
			.map(NoticeDTO::fromEntity)
			.collect(Collectors.toList());
	}

	public NoticeDTO getNoticeById(long id) {
		Notice notice = noticeMapper.selectById(id);
		return notice != null ? NoticeDTO.fromEntity(notice) : null;
	}

	public NoticeDTO createNotice(NoticeDTO noticeDTO) {
		Notice notice = noticeDTO.toEntity();
		notice.setCreateTime(LocalDateTime.now());
		noticeMapper.insert(notice);
		return NoticeDTO.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	public void updateNotice(NoticeDTO noticeDTO) {
		Notice notice = noticeDTO.toEntity();
		noticeMapper.update(notice);
	}

	public void deleteNotice(long id) {
		noticeMapper.delete(id);
	}

	// 캠페인을 위한 추가 메서드
	public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
		System.out.println("Service - Before save: " + campaignDTO);

		Notice notice = campaignDTO.toEntity();
		notice.setCreateTime(LocalDateTime.now());
		noticeMapper.insert(notice);

		System.out.println("Service - After save: " + noticeMapper.selectById(notice.getId()));
		return CampaignDTO.fromEntity(noticeMapper.selectById(notice.getId()));
	}

	public CampaignDTO getCampaignById(long id) {
		Notice notice = noticeMapper.selectById(id);
		return notice != null ? CampaignDTO.fromEntity(notice) : null;
	}
}
