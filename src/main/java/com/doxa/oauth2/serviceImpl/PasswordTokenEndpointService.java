package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.common.ClientCredentials;
import com.doxa.oauth2.common.GrantType;
import com.doxa.oauth2.config.oauth2.AuthorizationServerConfigurationProperties;
import com.doxa.oauth2.enums.AccessTokenFormat;
import com.doxa.oauth2.helpers.TokenEndpointHelper;
import com.doxa.oauth2.models.client.RegisteredClient;
import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.requests.TokenRequest;
import com.doxa.oauth2.responses.TokenResponse;
import com.doxa.oauth2.security.client.RegisteredClientAuthenticationService;
import com.doxa.oauth2.security.user.UserAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Service
public class PasswordTokenEndpointService {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordTokenEndpointService.class);

    @Autowired
    private UserAuthenticationService userAuthenticationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorizationServerConfigurationProperties authorizationServerProperties;
    @Autowired
    private RegisteredClientAuthenticationService registeredClientAuthenticationService;

    /**
     * ------------------ Access Token Request
     *
     * <p>The client makes a request to the token endpoint by adding the following parameters using
     * the "application/x-www-form-urlencoded" format per Appendix B with a character encoding of
     * UTF-8 in the HTTP request entity-body:
     *
     * <p>grant_type REQUIRED. Value MUST be set to "password".
     *
     * <p>username REQUIRED. The resource owner username.
     *
     * <p>password REQUIRED. The resource owner password.
     *
     * <p>scope OPTIONAL. The scope of the access request as described by Section 3.3.
     *
     * <p>If the client type is confidential or the client was issued client credentials (or assigned
     * other authentication requirements), the client MUST authenticate with the authorization server
     */
    public ResponseEntity<TokenResponse> getTokenResponseForPassword(
            String authorizationHeader, TokenRequest tokenRequest) {

        LOG.debug("Exchange token for resource owner password with [{}]", tokenRequest);

        ClientCredentials clientCredentials =
                TokenEndpointHelper.retrieveClientCredentials(authorizationHeader, tokenRequest);

        if (clientCredentials == null) {
            return TokenEndpointHelper.reportInvalidClientError();
        }

        RegisteredClient registeredClient;

        try {
            registeredClient =
                    registeredClientAuthenticationService.authenticate(
                            clientCredentials.getClientId(), clientCredentials.getClientSecret());

        } catch (AuthenticationException ex) {
            return TokenEndpointHelper.reportInvalidClientError();
        }

        if (registeredClient.getGrantTypes().contains(GrantType.PASSWORD)) {

            User authenticatedUser;
            try {
                authenticatedUser =
                        userAuthenticationService.authenticate(
                                tokenRequest.getUsername(), tokenRequest.getPassword());
            } catch (AuthenticationException ex) {
                return TokenEndpointHelper.reportUnauthorizedClientError();
            }

            Duration accessTokenLifetime = authorizationServerProperties.getAccessToken().getLifetime();
            Duration refreshTokenLifetime = authorizationServerProperties.getRefreshToken().getLifetime();

            Set<String> scopes = new HashSet<>();
            if (StringUtils.isNotBlank(tokenRequest.getScope())) {
                scopes = new HashSet<>(Arrays.asList(tokenRequest.getScope().split(" ")));
            }

            LOG.info(
                    "Creating token response for client credentials for client [{}]",
                    clientCredentials.getClientId());
            TokenResponse tokenResponse = new TokenResponse();
            String accessToken = AccessTokenFormat.JWT.equals(registeredClient.getAccessTokenFormat())
                    ? tokenService
                    .createPersonalizedJwtAccessToken(
                            authenticatedUser,
                            clientCredentials.getClientId(),
//                            null,
                            scopes,
                            accessTokenLifetime)
                    .getValue()
                    : tokenService
                    .createPersonalizedOpaqueAccessToken(
                            authenticatedUser, clientCredentials.getClientId(), scopes, accessTokenLifetime)
                    .getValue();

            String refreshToken = tokenService
                    .createPersonalizedRefreshToken(
                            clientCredentials.getClientId(), authenticatedUser, scopes, refreshTokenLifetime)
                    .getValue();

            tokenResponse.setToken_type(TokenResponse.BEARER_TOKEN_TYPE);
            tokenResponse.setAccess_token(accessToken);
            tokenResponse.setRefresh_token(refreshToken);
            tokenResponse.setExpires_in(accessTokenLifetime.toSeconds());
            return ResponseEntity.ok(tokenResponse);
        } else {
            return TokenEndpointHelper.reportUnauthorizedClientError();
        }
    }
}
