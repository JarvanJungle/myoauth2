package com.doxa.oauth2.models;

import com.doxa.oauth2.config.AppConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authorization_code")
public class AuthorizationCode {
    @Id
    private String id;
    @Field("redirect_uri")
    private String redirectUri;
    private Set<String> scopes;
    private String code;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConfig.DEFAULT_DATE_TIME_FORMAT, timezone = AppConfig.DEFAULT_TIMEZONE)
    private LocalDateTime expiry;
    @Field("client_id")
    private String clientId;
    @Field("code_challenge")
    private String codeChallenge;
    @Field("code_challenge_method")
    private String codeChallengeMethod;
    @Field("user_id")
    private String userId;


    public AuthorizationCode(
            String clientId,
            URI redirectUri,
            Set<String> scopes,
            String code,
            String userId,
            String nonce,
            String code_challenge,
            String code_challenge_method) {
        this.clientId = clientId;
        this.redirectUri = redirectUri.toString();
//        this.scopes = scopes.stream().reduce(" ", String::concat);
        this.scopes = scopes;
        this.userId = userId;
        this.code = code;
        this.expiry = LocalDateTime.now().plusMinutes(2);
    }

//    public Set<String> getScopes() {
//        List<String> scopesString = Arrays.asList(scopes.split(" "));
//        return new HashSet<>(scopesString);
//    }

    public URI getRedirectUri() {
        return URI.create(redirectUri);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getExpiry());
    }

//    @Override
//    public String toString() {
//        return "AuthorizationState{"
//                + "clientId='"
//                + clientId
//                + '\''
//                + ", redirectUri="
//                + redirectUri
//                + ", scopes="
//                + scopes
//                + ", code='"
//                + code
//                + '\''
//                + ", expiry="
//                + expiry
//                + ", subject="
//                + subject
//                + ", nonce="
//                + nonce
//                + ", code_challenge="
//                + code_challenge
//                + ", code_challenge_method="
//                + code_challenge_method
//                + '}';
//    }
}
