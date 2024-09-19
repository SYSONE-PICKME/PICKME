package project.pickme.notice.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.mapper.NoticeMapper;
import project.pickme.s3.service.S3Service;
import project.pickme.user.domain.Customs;
import project.pickme.user.repository.CustomsMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignService {

	private final NoticeMapper noticeMapper;
	private final CustomsMapper customsMapper;
	private final S3Service s3Service;

	public List<CampaignDto> getAllCampaigns() {
		return noticeMapper.selectByType("CAMPAIGN").stream()
			.map(CampaignDto::fromEntity)
			.collect(Collectors.toList());
	}

	public CampaignDto getCampaignById(Long id) {
		Notice notice = noticeMapper.selectById(id);
		if (notice == null || !"CAMPAIGN".equals(notice.getType())) {
			return null;
		}
		return CampaignDto.fromEntity(notice);
	}

	@Transactional
	public Long createCampaign(CampaignDto campaignDto) throws IOException {
		Customs customs = getCustomsById(campaignDto.getCustomsId());
		String imageUrl = null;
		if (campaignDto.getImageFile() != null && !campaignDto.getImageFile().isEmpty()) {
			imageUrl = s3Service.uploadImage(campaignDto.getImageFile());
		}
		Notice notice = campaignDto.toEntity(customs, imageUrl);
		noticeMapper.insert(notice);
		return notice.getId();
	}

	@Transactional
	public void updateCampaign(CampaignDto campaignDto) throws IOException {
		Customs customs = getCustomsById(campaignDto.getCustomsId());
		String imageUrl = null;
		Notice existingCampaign = noticeMapper.selectById(campaignDto.getId());
		if (campaignDto.getImageFile() != null && !campaignDto.getImageFile().isEmpty()) {
			deleteExistingImage(existingCampaign);
			imageUrl = s3Service.uploadImage(campaignDto.getImageFile());
		} else {
			Notice existingNotice = noticeMapper.selectById(campaignDto.getId());
			if (existingNotice != null && "CAMPAIGN".equals(existingNotice.getType())) {
				imageUrl = existingNotice.getContent();
			}
		}
		Notice notice = campaignDto.toEntity(customs, imageUrl);
		noticeMapper.update(notice);
	}

	@Transactional
	public void deleteCampaign(Long id) {
		Notice existingCampaign = noticeMapper.selectById(id);
		deleteExistingImage(existingCampaign);
		noticeMapper.delete(id);
	}

	private Customs getCustomsById(String customsId) {
		return customsMapper.findByCustomsId(customsId).get();		// todo: Optional은 예외처리 해야한다고 함
	}

	private void deleteExistingImage(Notice existingCampaign) {
		if (existingCampaign != null && "CAMPAIGN".equals(existingCampaign.getType()) && existingCampaign.getContent() != null) {
			s3Service.deleteFile(existingCampaign.getContent());
		}
	}

}
