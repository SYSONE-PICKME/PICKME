package project.pickme.notice.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import project.pickme.customs.domain.Customs;

@Getter
@ToString
public class Notice {
	private Long id;
	// private String customs_id;
	private String title;
	private String content;
	private LocalDateTime createTime;
	private Customs customs;

	public Notice(Long id, String title, String content, LocalDateTime createTime, Customs customs) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.customs = customs;
	}

	public Notice(){}
}
