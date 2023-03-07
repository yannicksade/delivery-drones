package com.musala.delivery.drones.enumerations;

public enum EModel {
    LIGHTWEIGHT(0), MIDDLEWEIGHT(1), CRUISERWEIGHT(2), HEAVYWEIGHT(3);

    int code;

    EModel(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        switch (this) {
            case LIGHTWEIGHT:
                return "Lightweight";
            case MIDDLEWEIGHT:
                return "Middleweight";
            case CRUISERWEIGHT:
                return "Cruiserweight";
            case HEAVYWEIGHT:
                return "Heavyweight";
            default:
                throw new IllegalStateException("Invalid model");
        }
    }
}
