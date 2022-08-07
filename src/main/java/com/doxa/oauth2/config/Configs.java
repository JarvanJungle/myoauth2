package com.doxa.oauth2.config;

public enum Configs {

    //	entity representative roles
    DOXA_ADMIN("DOXA_ADMIN"),
    ENTITY_ADMIN("ENTITY_ADMIN"),
    ENTITY_USER("ENTITY_USER"),
    COMPANY_ADMIN("COMPANY_ADMIN"),
    ENTITY_ADMIN_ROLE("Entity Administrator"),


    DEFAULT_LANGUAGE("en"),
    DEFAULT_PWD_LENGTH("15");

    private final String config;

    Configs(String config) {
        this.config = config;
    }

    public String getValue() {
        return this.config;
    }
}
