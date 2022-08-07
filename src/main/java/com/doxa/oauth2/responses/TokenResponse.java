package com.doxa.oauth2.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Token Response as specified by:
 *
 * <p>OAuth 2.0 (https://www.rfc-editor.org/rfc/rfc6749.html#section-4.1.3) OpenID Connect 1.0
 * (https://openid.net/specs/openid-connect-core-1_0.html#TokenRequest)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    public static final String BEARER_TOKEN_TYPE = "Bearer";

    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String id_token;
    private String error;

    public TokenResponse(String invalid_client) {
        this.error = invalid_client;
    }

    @Override
    public String toString() {
        return "TokenResponse{"
                + "access_token='"
                + access_token
                + '\''
                + ", token_type='"
                + token_type
                + '\''
                + ", refresh_token='"
                + refresh_token
                + '\''
                + ", expires_in="
                + expires_in
                + ", id_token='"
                + id_token
                + '\''
                + '}';
    }
}
