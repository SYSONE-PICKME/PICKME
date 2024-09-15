package project.pickme.notice.dto;

import java.time.LocalDateTime;

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
@Builder
public class NoticeDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private String customsId;
	private String customsName;
	private Role customsRole;

	public Notice toEntity(Customs customs) {
		return Notice.builder()
			.id(id)
			.title(title)
			.content(content)
			.createTime(createTime)
			.customs(customs)
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

	public static NoticeDto updateNoticeDto(Long id, NoticeDto noticeDto) {
		return NoticeDto.builder()
			.id(id)
			.title(noticeDto.getTitle())
			.content(noticeDto.getContent())
			.customsId(noticeDto.getCustomsId())
			.build();
	}
}