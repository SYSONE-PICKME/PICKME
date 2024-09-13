package project.pickme.notice.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.user.constant.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CampaignDto extends NoticeDto {
	private String imageUrl;
	private MultipartFile imageFile;

	@Builder(builderMethodName = "campaignBuilder")
	public CampaignDto(Long id, String title, String content, LocalDateTime createTime,
		String customsId, String customsName, Role customsRole,
		String imageUrl, MultipartFile imageFile) {
		super(id, title, content, createTime, customsId, customsName, customsRole);
		this.imageUrl = imageUrl;
		this.imageFile = imageFile;
	}

	@Override
	public Notice toEntity() {
		Notice notice = super.toEntity();
		if (this.imageUrl != null) {
			notice.setContent(notice.getContent() + "\n[Image URL: " + this.imageUrl + "]");
		}
		return notice;
	}

	public static CampaignDto fromEntity(Notice notice) {
		NoticeDto baseDto = NoticeDto.fromEntity(notice);
		String content = notice.getContent();
		String imageUrl = null;

		int index = content.lastIndexOf("[Image URL: ");
		if (index != -1) {
			imageUrl = content.substring(index + 12, content.length() - 1);
			content = content.substring(0, index).trim();
		}

		return CampaignDto.campaignBuilder()
			.id(baseDto.getId())
			.title(baseDto.getTitle())
			.content(content)
			.createTime(baseDto.getCreateTime())
			.customsId(baseDto.getCustomsId())
			.customsName(baseDto.getCustomsName())
			.customsRole(baseDto.getCustomsRole())
			.imageUrl(imageUrl)
			.build();
	}

}
