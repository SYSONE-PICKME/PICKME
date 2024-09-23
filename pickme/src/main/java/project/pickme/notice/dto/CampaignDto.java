package project.pickme.notice.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.notice.constant.NoticeType;
import project.pickme.notice.domain.Notice;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.Customs;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDto {
	private Long id;
	private String title;
	private LocalDateTime createTime;
	private String customsId;
	private String customsName;
	private MultipartFile imageFile;
	private String imageUrl;

}
