package com.musala.delivery.drones.enumerations;

public enum EModel {
	LIGHTWEIGHT(0), MIDDLEWEIGHT(1), CRUISERWEIGHT(2), HEAVYWEIGHT(3);

	int code;

	EModel(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return switch (this) {
		case LIGHTWEIGHT -> "Lightweight";
		case MIDDLEWEIGHT -> "Middleweight";
		case CRUISERWEIGHT -> "Cruiserweight";
		case HEAVYWEIGHT -> "Heavyweight";
		default -> throw new IllegalStateException("Invalid model");
		};
	}
}
