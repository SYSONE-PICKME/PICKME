package project.pickme.notice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pickme.notice.domain.Notice;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private String customsId;
	private String customsName;
	private Role customsRole;

	public Notice toEntity() {
		return Notice.builder()
			.id(id)
			.title(title)
			.content(tcontent)
			.createTime(createTime)
			.customs(Customs.createCustoms(customsId, "", customsName, ""))
			.build();
	}

	public static NoticeDto fromEntity(Notice notice) {
		Customs customs = notice.getCustoms();
		return NoticeDto.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.createTime(notice.getCreateTime())
			.customsId(customs.getId())
			.customsName(customs.getName())
			.customsRole(customs.getRole())
			.build();
	}

	public void updateEntity(Notice notice) {
		notice.updateTitle(this.title);
		notice.updateContent(this.content);
	}
}
