package project.pickme.notice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
