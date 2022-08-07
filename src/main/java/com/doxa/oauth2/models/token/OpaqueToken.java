package com.doxa.oauth2.models.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.authentication.BadCredentialsException;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "opaque_token")
public class OpaqueToken extends Token {

    @NotBlank
    @Size(max = 200)
    private String subject;

    @NotBlank
    @Size(max = 200)
    private String clientId;

    @NotBlank
    @Size(max = 200)
    private String issuer;

    @NotNull
    private String scope;

    @NotNull
    private LocalDateTime issuedAt;

    @NotNull
    private LocalDateTime notBefore;

    @NotNull
    private boolean refreshToken;


    @Override
    public boolean isReferenceToken() {
        return true;
    }

    public void validate() {
        if (LocalDateTime.now().isAfter(this.getExpiry())) {
            throw new BadCredentialsException("Expired");
        }
        if (LocalDateTime.now().isBefore(this.getNotBefore())) {
            throw new BadCredentialsException("Not yet valid");
        }
    }

    public Set<String> getScope() {
        return new HashSet<>(Arrays.asList(scope.split(",")));
    }

    public void setScope(Set<String> scope) {
        this.scope = String.join(",", scope);
    }

    @Override
    public String toString() {
        return "OpaqueToken{" +
                "subject='" + subject + '\'' +
                ", clientId='" + clientId + '\'' +
                ", issuer='" + issuer + '\'' +
                ", scope='" + getScope() + '\'' +
                ", issuedAt=" + issuedAt +
                ", notBefore=" + notBefore +
                ", refreshToken=" + refreshToken +
                "} " + super.toString();
    }
}
