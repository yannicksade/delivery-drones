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
		case LIGHTWEIGHT -> "LIGHTWEIGHT";
		case MIDDLEWEIGHT -> "MIDDLEWEIGHT";
		case CRUISERWEIGHT -> "CRUISERWEIGHT";
		case HEAVYWEIGHT -> "HEAVYWEIGHT";
		default -> throw new IllegalStateException("Invalid model");
		};
	}
}
