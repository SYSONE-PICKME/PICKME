package project.pickme.notice.dto;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.notice.domain.Notice;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto extends NoticeDto {
	private String imageUrl;
	private MultipartFile imageFile;

	@Builder(builderMethodName = "campaignBuilder")
	public CampaignDto(
		Long id,
		String title,
		String content,
		LocalDateTime createTime,
		String customsId,
		String customsName,
		Role customsRole,
		String imageUrl,
		MultipartFile imageFile) {
		super(id, title, content, createTime, customsId, customsName, customsRole);
		this.imageUrl = imageUrl;
		this.imageFile = imageFile;
	}

	@Override
	public Notice toEntity(Customs customs) {
		String fullContent = getContent();
		if (this.imageUrl != null && !this.imageUrl.isEmpty()) {
			fullContent += "\n[Image URL: " + this.imageUrl + "]";
		}
		return Notice.builder()
			.id(getId())
			.title(getTitle())
			.content(fullContent)
			.createTime(getCreateTime())
			.customs(customs)
			.build();
	}

	public static CampaignDto fromEntity(Notice notice) {
		String content = notice.getContent();
		String imageUrl = null;
		if (content != null) {
			int index = content.lastIndexOf("[Image URL: ");
			if (index != -1) {
				imageUrl = content.substring(index + 12, content.length() - 1);
				content = content.substring(0, index).trim();
			}
		}
		return CampaignDto.campaignBuilder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(content)
			.createTime(notice.getCreateTime())
			.customsId(notice.getCustoms().getId())
			.customsName(notice.getCustoms().getName())
			.customsRole(notice.getCustoms().getRole())
			.imageUrl(imageUrl)
			.build();
	}
}
