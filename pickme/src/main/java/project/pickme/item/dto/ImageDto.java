package project.pickme.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDto {
	private long itemId;
	private String name;
	private String url;
	private int seq;
}
