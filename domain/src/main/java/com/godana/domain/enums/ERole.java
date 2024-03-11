package com.godana.domain.enums;

public enum ERole {
    ROLE_ADMIN("ADMIN"),
    ROLe_USER("USER");

    private final String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
