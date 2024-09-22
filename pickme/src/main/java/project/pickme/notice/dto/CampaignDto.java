package project.pickme.notice.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.notice.constant.NoticeType;
import project.pickme.notice.domain.Notice;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDto {
	private Long id;
	private String title;
	private LocalDateTime createTime;
	private String customsId;
	private String customsName;
	private MultipartFile imageFile;
	private String imageUrl;

	public CampaignDto withId(Long id) {
		return CampaignDto.builder()
			.id(id)
			.title(title)
			.createTime(createTime)
			.customsId(customsId)
			.customsName(customsName)
			.imageFile(imageFile)
			.imageUrl(imageUrl)
			.build();
	}

	public Notice toEntity(Customs customs, String imageUrl) {
		return Notice.builder()
			.id(id)
			.title(title)
			.content(imageUrl)
			.createTime(createTime != null ? createTime : LocalDateTime.now())
			.type(NoticeType.CAMPAIGN)
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
