package project.pickme.user.constant;

import lombok.Getter;

@Getter
public enum Type {
	USER("개인"),
	BUSINESS("사업자"),
	BOTH("제한 없음");

	private final String value;

	Type(String value) {
		this.value = value;
	}
}
