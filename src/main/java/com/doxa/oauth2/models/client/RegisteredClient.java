package com.doxa.oauth2.models.client;

import com.doxa.oauth2.common.GrantType;
import com.doxa.oauth2.enums.AccessTokenFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "registered_client")
public class RegisteredClient {

    public static final URI DEFAULT_REDIRECT_URI = URI.create("http://localhost:9090/demo-client/login/oauth2/code/demo");
    @Id
    private String id;
    @Field("client_secret")
    private String clientSecret;
    private boolean confidential;
    @Field("access_token_format")
    private String accessTokenFormat;
    @Field("redirect_uris")
    private List<String> redirectUris;
    @Field("grant_types")
    private List<String> grantTypes;

    public RegisteredClient() {
    }

    public RegisteredClient(String id, String clientSecret, boolean confidential, String accessTokenFormat, List<String> grantTypes, List<String> redirectUris) {
        this.id = id;
        this.clientSecret = clientSecret;
        this.confidential = confidential;
        this.accessTokenFormat = accessTokenFormat;
        this.grantTypes = grantTypes;
        this.redirectUris = redirectUris;
    }

//    public RegisteredClient(
//            UUID identifier,
//            String clientId,
//            String clientSecret,
//            boolean confidential,
//            AccessTokenFormat accessTokenFormat,
//            Set<GrantType> grantTypes,
//            Set<String> redirectUris,
//            Set<String> corsUris) {
//        this.identifier = identifier;
//        this.accessTokenFormat = accessTokenFormat;
//        this.clientId = clientId;
//        this.clientSecret = clientSecret;
//        this.confidential = confidential;
//        this.grantTypes = grantTypes != null ? grantTypes.stream().map(Objects::toString)
//                .collect(Collectors.joining(",")) : null;
//        ;
////    this.corsUris = corsUris;
//        this.redirectUris = redirectUris != null ? redirectUris.stream().map(Objects::toString)
//                .collect(Collectors.joining(",")) : null;
//    }

//    public Set<String> getRedirectUris() {
//        return new HashSet<>(Arrays.asList(redirectUris.split(",")));
//    }

//    public Set<String> getCorsUris() {
//        Set<String> redirectUris = new HashSet<>();
//        redirectUris.add("*");
//        return redirectUris;
//    }

//    public Set<GrantType> getGrantTypes() {
//        return Arrays.stream(grantTypes.split(",")).map(grant -> GrantType.valueOf(grant.toUpperCase())).collect(Collectors.toSet());
//    }

//    @Override
//    public String toString() {
//        return "RegisteredClient{"
//                + "identifier='"
//                + identifier
//                + '\''
//                + ", clientId='"
//                + clientId
//                + ", clientSecret='*****'"
//                + ", confidential="
//                + confidential
//                + ", accessTokenFormat="
//                + accessTokenFormat
//                + ", grantTypes="
//                + grantTypes
//                + ", redirectUris="
//                + redirectUris
//                + ", corsUris="
//                + corsUris
//                + '}';
//    }
}
