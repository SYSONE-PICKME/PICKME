package project.pickme.notice.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.pickme.notice.constant.Type;
import project.pickme.user.domain.Customs;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Notice {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private Type type;
	private Customs customs;

}
