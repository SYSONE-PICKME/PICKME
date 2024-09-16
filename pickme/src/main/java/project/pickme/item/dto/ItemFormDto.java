package project.pickme.item.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pickme.user.constant.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFormDto {
	private MultipartFile[] files;
	private String name;
	private int code;
	private Type type;
	private long price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long[] lawId;
}
