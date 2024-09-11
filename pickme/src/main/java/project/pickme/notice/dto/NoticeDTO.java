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
public class NoticeDTO {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private String customsId;
	private String customsName;
	private Role customsRole;

	public Notice toEntity() {
		return Notice.builder()
			.id(this.id)
			.title(this.title)
			.content(this.content)
			.createTime(this.createTime)
			.customs(Customs.createCustoms(this.customsId, "", this.customsName, ""))  // 이름 정보 추가
			.build();
	}

	public static NoticeDTO fromEntity(Notice notice) {
		Customs customs = notice.getCustoms();
		return NoticeDTO.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.createTime(notice.getCreateTime())
			.customsId(notice.getCustoms().getId())
			.customsName(customs.getName())
			.customsRole(customs.getRole())
			.build();
	}
}
