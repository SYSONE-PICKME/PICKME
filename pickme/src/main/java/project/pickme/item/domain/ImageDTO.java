package project.pickme.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDTO {

	private Long item_id;
	private String name;
	private String url;
	private Long seq;

}
