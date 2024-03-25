package com.godana.domain.enums;

public enum EPlaceStatus {
    PENDING("PENDING"),
    DONE("DONE");

    private final String value;

    EPlaceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
