package project.pickme.notice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.customs.domain.Customs;

@Getter
@AllArgsConstructor
public class Notice {
	private Long id;
	private String title;
	private String content;
	private Customs customs;
}
