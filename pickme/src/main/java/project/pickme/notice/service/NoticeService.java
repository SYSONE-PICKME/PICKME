package project.pickme.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import project.pickme.config.S3Config;
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
	private final AmazonS3Client amazonS3Client;
	private final S3Config s3Config;

	public List<NoticeDto> getAllNotices() {
		return noticeMapper.selectAll().stream()
			.map(this::convertToAppropriateDto)
			.collect(Collectors.toList());
	}

	public NoticeDto getNoticeById(Long id) {
		if (id == null) {
			return null;
		}
		Notice notice = noticeMapper.selectById(id);
		return convertToAppropriateDto(notice);
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
	public CampaignDto createCampaign(CampaignDto campaignDto) {
		Customs customs = getCustomsById(campaignDto.getCustomsId());
		Notice notice = campaignDto.toEntity(customs);
		noticeMapper.insert(notice);
		return CampaignDto.fromEntity(notice);
	}

	@Transactional
	public void updateCampaign(CampaignDto campaignDto) {
		Customs customs = getCustomsById(campaignDto.getCustomsId());
		Notice existingNotice = noticeMapper.selectById(campaignDto.getId());
		String existingImageUrl = extractImageUrl(existingNotice.getContent());
		if (campaignDto.getImageUrl() != null && !campaignDto.getImageUrl().equals(existingImageUrl)) {
			deleteExistingImage(existingImageUrl);
		}
		Notice updatedNotice = campaignDto.toEntity(customs);
		noticeMapper.update(updatedNotice);
	}

	@Transactional
	public void deleteNotice(Long id) {
		noticeMapper.delete(id);
	}

	private NoticeDto convertToAppropriateDto(Notice notice) {
		if (notice == null) {
			return null;
		}
		String content = notice.getContent();
		if (content == null) {
			return NoticeDto.fromEntity(notice);
		}
		if (content.contains("[Image URL: ")) {
			return CampaignDto.fromEntity(notice);
		}
		return NoticeDto.fromEntity(notice);
	}

	private String extractImageUrl(String content) {
		int start = content.lastIndexOf("[Image URL: ");
		if (start != -1) {
			start += 12;
			int end = content.indexOf("]", start);
			return content.substring(start, end);
		}
		return null;
	}

	private String updateImageUrlInContent(String content, String newImageUrl) {
		if (content == null) {
			return "[Image URL: " + newImageUrl + "]";
		}
		int index = content.lastIndexOf("[Image URL: ");
		if (index != -1) {
			return content.substring(0, index) + "[Image URL: " + newImageUrl + "]";
		} else {
			return content + "\n[Image URL: " + newImageUrl + "]";
		}
	}

	private void deleteExistingImage(String imageUrl) {
		if (imageUrl != null) {
			String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
			amazonS3Client.deleteObject(s3Config.getBucket(), fileName);
		}
	}

	private Customs getCustomsById(String customsId) {
		return customsMapper.findById(customsId).get();
	}
}
