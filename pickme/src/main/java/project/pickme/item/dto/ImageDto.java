package project.pickme.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDto {
	private Long item_id;
	private String name;
	private String url;
	private Long seq;
}
