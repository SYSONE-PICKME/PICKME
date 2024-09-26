package project.pickme.notice.service;

import static project.pickme.user.exception.UserErrorCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.notice.converter.CampaignConverter;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.repository.NoticeMapper;
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
		return noticeMapper.selectAllCampaigns().stream()
			.map(CampaignConverter::fromEntity)
			.toList();
	}

	public CampaignDto getCampaignById(Long id) {
		return CampaignConverter.fromEntity(noticeMapper.selectById(id));
	}

	@Transactional
	public Long createCampaign(CampaignDto campaignDto, Customs customs) throws IOException {
		String imageUrl = updateImage(campaignDto, null);
		Notice notice = CampaignConverter.toEntity(customs, imageUrl, campaignDto);
		noticeMapper.insert(notice);
		return notice.getId();
	}

	@Transactional
	public void updateCampaign(CampaignDto campaignDto, Customs customs) throws IOException {
		Notice campaign = noticeMapper.selectById(campaignDto.getId());
		String imageUrl = updateImage(campaignDto, campaign);
		noticeMapper.update(CampaignConverter.toEntity(customs, imageUrl, campaignDto));
	}

	@Transactional
	public void deleteCampaign(Long id) {
		Notice campaign = noticeMapper.selectById(id);
		s3Service.deleteFile(campaign.getContent());
		noticeMapper.delete(id);
	}

	private Customs getCustomsById(String customsId) {
		return customsMapper.findByCustomsId(customsId).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMS));
	}

	private String updateImage(CampaignDto campaignDto, Notice campaign) {
		if (campaign != null && campaign.getContent() != null) {
			s3Service.deleteFile(campaign.getContent());
			return s3Service.uploadImage(campaignDto.getImageFile());
		}
		return s3Service.uploadImage(campaignDto.getImageFile());
	}

	public List<String> get4Campaigns() {
		return noticeMapper.select4Campaigns();
	}
}
