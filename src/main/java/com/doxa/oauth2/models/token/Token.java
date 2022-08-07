package com.doxa.oauth2.models.token;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document(collection = "token")
public abstract class Token{

    @Id
    private String id;

    @NotBlank
    @Size(max = 20000)
    private String value;

    @NotNull
    private LocalDateTime expiry;

    private boolean revoked;
    @Field("access_token")
    private boolean accessToken;

    public Token() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public abstract boolean isReferenceToken();

    @Override
    public String toString() {
        return "Token{"
                + "value='"
                + value
                + '\''
                + ", expiry="
                + expiry
                + ", revoked="
                + revoked
                + ", referenceToken="
                + isReferenceToken()
                + '}';
    }
}
