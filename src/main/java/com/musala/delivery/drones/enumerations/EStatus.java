package com.musala.delivery.drones.enumerations;

public enum EStatus {
    IDLE(0), LOADING(1), LOADED(2), DELIVERING(3), DELIVERED(4), RETURNING(5);

    public int code;

    EStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return switch (this) {
            case IDLE -> "IDLE";
            case LOADING -> "LOADING";
            case LOADED -> "LOADED";
            case DELIVERING -> "DELIVERING";
            case DELIVERED -> "DELIVERED";
            case RETURNING -> "RETURNING";
            default -> throw new IllegalStateException("Invalid state");
        };
    }
}
