package com.doxa.oauth2.models.token;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "jsonwebtoken")
public class JsonWebToken extends Token {

    @NotNull
    private boolean accessToken;

    public boolean isAccessToken() {
        return accessToken;
    }

    public void setAccessToken(boolean idToken) {
        this.accessToken = idToken;
    }

    @Override
    public boolean isReferenceToken() {
        return false;
    }

    @Override
    public String toString() {
        return "JsonWebToken{" +
                "idToken=" + accessToken +
                "} " + super.toString();
    }
}
