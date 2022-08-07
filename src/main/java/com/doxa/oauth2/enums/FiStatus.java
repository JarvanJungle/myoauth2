package com.doxa.oauth2.enums;

public enum FiStatus {
    ASSOCIATED("ASSOCIATED"),
    DEACTIVATED("DEACTIVATED");
    private final String config;
    FiStatus(String config) {
        this.config = config;
    }
    public String getValue() {
        return this.config;
    }
}
