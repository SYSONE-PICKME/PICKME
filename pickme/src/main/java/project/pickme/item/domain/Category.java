package project.pickme.item.domain;

import lombok.Getter;

@Getter
public enum Category {
	CLOTHES("01", "의류"),
	DAILY("02", "생활용품"),
	DIGITAL("03", "디지털"),
	FURNITURE("04", "가구"),
	ETC("10", "기타");

	private final String code;
	private final String name;

	Category(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
