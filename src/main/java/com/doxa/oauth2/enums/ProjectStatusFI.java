package com.doxa.oauth2.enums;

public enum ProjectStatusFI {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");
    private final String constMessages;
    ProjectStatusFI(String constMessages) {
        this.constMessages = constMessages;
    }
    public String getValue() {
        return constMessages;
    }
}
