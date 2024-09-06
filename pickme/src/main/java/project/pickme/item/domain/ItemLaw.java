package project.pickme.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemLaw {
	private Long id;
	private Item item;
	private Law law;
}
