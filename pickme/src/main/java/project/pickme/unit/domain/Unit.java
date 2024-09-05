package project.pickme.unit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Unit {
	private long startScope;
	private long endScope;
	private int priceUnit;
}
