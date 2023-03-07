package com.musala.delivery.drones.enumerations;

import lombok.Data;

@Data
public enum EStatus {
    IDLE(0), LOADING(1), LOADED(2),
    DELIVERING(3), DELIVERED(4), RETURNING(5);

    int code;

    EStatus(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        switch (this) {
            case IDLE:
                return "IDLE";
            case LOADING:
                return "LOADING";
            case LOADED:
                return "LOADED";
            case DELIVERING:
                return "DELIVERING";
            case DELIVERED:
                return "DELIVERED";
            case RETURNING:
                return "RETURNING";
            default:
                throw new IllegalStateException("Invalid state");
        }
    }
}
