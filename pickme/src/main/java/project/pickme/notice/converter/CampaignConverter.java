package project.pickme.notice.converter;

import java.time.LocalDateTime;

import project.pickme.notice.constant.Type;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.user.domain.Customs;

public class CampaignConverter {

	public static Notice toEntity(Customs customs, String imageUrl, CampaignDto campaignDto) {
		return Notice.builder()
			.id(campaignDto.getId())
			.title(campaignDto.getTitle())
			.content(imageUrl)
			.createTime(campaignDto.getCreateTime() != null ? campaignDto.getCreateTime() : LocalDateTime.now())
			.type(Type.CAMPAIGN)
			.customs(customs)
			.build();
	}

	public static CampaignDto fromEntity(Notice notice) {
		Customs customs = notice.getCustoms();
		return CampaignDto.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.createTime(notice.getCreateTime())
			.customsId(customs.getId())
			.customsName(customs.getName())
			.imageUrl(notice.getContent())
			.build();
	}
}
