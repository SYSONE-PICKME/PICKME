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

	public NoticeDto withId(Long id) {
		return NoticeDto.builder()
			.id(id)
			.title(title)
			.content(content)
			.createTime(createTime)
			.customsId(customsId)
			.customsName(customsName)
			.build();
	}

	public Notice toEntity(Customs customs) {
		return Notice.builder()
			.id(id)
			.title(title)
			.content(content)
			.createTime(createTime != null ? createTime : LocalDateTime.now())
			.type(Notice.NoticeType.NOTICE)
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
			.build();
	}
}
