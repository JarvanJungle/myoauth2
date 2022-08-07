package com.doxa.oauth2.helpers;

import com.doxa.oauth2.common.AuthenticationUtil;
import com.doxa.oauth2.common.ClientCredentials;
import com.doxa.oauth2.requests.TokenRequest;
import com.doxa.oauth2.responses.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class TokenEndpointHelper {

    private TokenEndpointHelper() {
    }

    public static ClientCredentials retrieveClientCredentials(
            String authorizationHeader, TokenRequest tokenRequest) {
        ClientCredentials clientCredentials = null;
        if (authorizationHeader != null) {
            clientCredentials = AuthenticationUtil.fromBasicAuthHeader(authorizationHeader);
        } else if (StringUtils.isNotBlank(tokenRequest.getClient_id())) {
            clientCredentials =
                    new ClientCredentials(tokenRequest.getClient_id(), tokenRequest.getClient_secret());
        }
        return clientCredentials;
    }

    public static ResponseEntity<TokenResponse> reportUnauthorizedClientError() {
        return ResponseEntity.badRequest().body(new TokenResponse("unauthorized_client"));
    }

    public static ResponseEntity<TokenResponse> reportInvalidClientError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic")
                .body(new TokenResponse("invalid_client"));
    }

    public static ResponseEntity<TokenResponse> reportInvalidGrantError() {
        return ResponseEntity.badRequest().body(new TokenResponse("invalid_grant"));
    }
}
