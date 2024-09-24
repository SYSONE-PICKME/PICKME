package project.pickme.notice.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
