package project.pickme.notice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
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
			.toList();
	}

	public CampaignDto getCampaignById(Long id) {
		Notice notice = noticeMapper.selectById(id);
		if (notice == null || !"CAMPAIGN".equals(notice.getType())) {
			return null;
		}
		return CampaignDto.fromEntity(notice);
	}

	@Transactional
	public Long createCampaign(CampaignDto campaignDto, @CurrentUser Customs customs) throws IOException {
		String imageUrl = processImageUpload(campaignDto, null);
		Notice notice = campaignDto.toEntity(customs, imageUrl);
		noticeMapper.insert(notice);
		return notice.getId();
	}

	@Transactional
	public void updateCampaign(CampaignDto campaignDto, @CurrentUser Customs customs) throws IOException {
		Notice existingCampaign = noticeMapper.selectById(campaignDto.getId());
		String imageUrl = processImageUpload(campaignDto, existingCampaign);
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

	private String processImageUpload(CampaignDto campaignDto, Notice existingCampaign) {
		if (campaignDto.getImageFile() != null && !campaignDto.getImageFile().isEmpty()) {
			if (existingCampaign != null) {
				deleteExistingImage(existingCampaign);
			}
			return s3Service.uploadImage(campaignDto.getImageFile());
		}
		if (existingCampaign != null && "CAMPAIGN".equals(existingCampaign.getType())) {
			return existingCampaign.getContent();
		}
		return null;
	}

}
