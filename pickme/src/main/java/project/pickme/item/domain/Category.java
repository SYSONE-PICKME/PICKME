package project.pickme.item.domain;

import lombok.Getter;

@Getter
public enum Category {
	CLOTHES(1, "의류"),
	DAILY(2, "생활용품"),
	DIGITAL(3, "디지털"),
	FURNITURE(4, "가구"),
	ETC(10, "기타");

	private final int code;
	private final String name;

	Category(int code, String name) {
		this.code = code;
		this.name = name;
	}
}
