package project.pickme.item.dto;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.format.annotation.DateTimeFormat;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;
	private long[] lawId;
}
