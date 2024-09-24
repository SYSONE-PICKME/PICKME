package project.pickme.notice.converter;

import java.time.LocalDateTime;

import project.pickme.notice.constant.NoticeType;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.user.domain.Customs;

public class NoticeConverter {

	public static Notice toEntity(Customs customs, NoticeDto noticeDto) {
		return Notice.builder()
			.id(noticeDto.getId())
			.title(noticeDto.getTitle())
			.content(noticeDto.getContent())
			.createTime(noticeDto.getCreateTime() != null ? noticeDto.getCreateTime() : LocalDateTime.now())
			.type(NoticeType.NOTICE)
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
