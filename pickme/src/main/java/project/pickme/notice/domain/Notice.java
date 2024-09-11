package project.pickme.notice.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pickme.user.domain.Customs;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Notice {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private Customs customs;

	public static Notice createEmptyNoticeForForm() {
		return Notice.builder()
			.title("")
			.content("")
			.build();
	}
}
