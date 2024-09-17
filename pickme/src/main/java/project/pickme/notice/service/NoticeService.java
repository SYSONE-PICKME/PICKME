package project.pickme.notice.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.mapper.NoticeMapper;
import project.pickme.s3.config.S3Config;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;
import project.pickme.util.S3FileUploadService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;
	private final CustomsMapper customsMapper;

	public List<NoticeDto> getAllNotices() {
		return noticeMapper.selectByType("NOTICE").stream()
			.map(NoticeDto::fromEntity)
			.collect(Collectors.toList());
	}

	public NoticeDto getNoticeById(Long id) {
		Notice notice = noticeMapper.selectById(id);
		if (notice == null || !"NOTICE".equals(notice.getType())) {
			return null;
		}
		return NoticeDto.fromEntity(notice);
	}

	@Transactional
	public Long createNotice(NoticeDto noticeDto) {
		Customs customs = getCustomsById(noticeDto.getCustomsId());
		Notice notice = noticeDto.toEntity(customs);
		noticeMapper.insert(notice);
		return notice.getId();
	}

	@Transactional
	public void updateNotice(NoticeDto noticeDto) {
		Customs customs = getCustomsById(noticeDto.getCustomsId());
		Notice notice = noticeDto.toEntity(customs);
		noticeMapper.update(notice);
	}

	@Transactional
	public void deleteNotice(Long id) {
		noticeMapper.delete(id);
	}

	private Customs getCustomsById(String customsId) {
		return customsMapper.findByCustomsId(customsId).get();			//todo: 예외처리 해야함
	}
}
