package project.pickme.notice.service;

import static project.pickme.user.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.notice.converter.NoticeConverter;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.repository.NoticeMapper;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;
	private final CustomsMapper customsMapper;

	public List<NoticeDto> getAllNotices() {
		return noticeMapper.selectAllNotices().stream()
			.map(NoticeConverter::fromEntity)
			.toList();
	}

	public NoticeDto getNoticeById(Long id) {
		return NoticeConverter.fromEntity(noticeMapper.selectById(id));
	}

	@Transactional
	public Long createNotice(NoticeDto noticeDto, Customs customs) {
		Notice notice = NoticeConverter.toEntity(customs, noticeDto);
		noticeMapper.insert(notice);
		return notice.getId();
	}

	@Transactional
	public void updateNotice(NoticeDto noticeDto, Customs customs) {
		noticeMapper.update(NoticeConverter.toEntity(customs, noticeDto));
	}

	@Transactional
	public void deleteNotice(Long id) {
		noticeMapper.delete(id);
	}

	private Customs getCustomsById(String customsId) {
		return customsMapper.findByCustomsId(customsId).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMS));
	}

	public List<NoticeDto> getUserNotices(int page, int size){
		int offset = page * size;
		return noticeMapper.selectUserNotices(offset, size).stream()
			.map(NoticeConverter::fromEntity)
			.toList();
	}
}
