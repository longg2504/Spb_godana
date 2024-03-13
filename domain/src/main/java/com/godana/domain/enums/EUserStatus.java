package com.godana.domain.enums;

public enum EUserStatus {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE");

    private final String value;

    EUserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
