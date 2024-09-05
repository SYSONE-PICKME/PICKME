package project.pickme.image.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.pickme.item.domain.Item;

@Getter
@AllArgsConstructor
public class Image {
	private Long id;
	private String url;
	private String name;
	private int seq;
	private Item item;
}
