package com.doxa.oauth2.serviceImpl;
import com.doxa.oauth2.common.ClientCredentials;
import com.doxa.oauth2.common.GrantType;
import com.doxa.oauth2.config.oauth2.AuthorizationServerConfigurationProperties;
import com.doxa.oauth2.enums.AccessTokenFormat;
import com.doxa.oauth2.helpers.TokenEndpointHelper;
import com.doxa.oauth2.models.client.RegisteredClient;
import com.doxa.oauth2.requests.TokenRequest;
import com.doxa.oauth2.responses.TokenResponse;
import com.doxa.oauth2.security.client.RegisteredClientAuthenticationService;
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

import static com.doxa.oauth2.responses.TokenResponse.BEARER_TOKEN_TYPE;


@Service
public class ClientCredentialsTokenEndpointService {
  private static final Logger LOG =
      LoggerFactory.getLogger(ClientCredentialsTokenEndpointService.class);

  @Autowired
  private TokenService tokenService;
  @Autowired
  private AuthorizationServerConfigurationProperties authorizationServerProperties;
  @Autowired
  private RegisteredClientAuthenticationService registeredClientAuthenticationService;


  /* -------------------
  Access Token Request

  The client makes a request to the token endpoint by adding the
  following parameters using the "application/x-www-form-urlencoded"
  format per Appendix B with a character encoding of UTF-8 in the HTTP
  request entity-body:

  grant_type
        REQUIRED.  Value MUST be set to "client_credentials".

  scope
        OPTIONAL.  The scope of the access request as described by
        Section 3.3.

  The client MUST authenticate with the authorization server
  */
  public ResponseEntity<TokenResponse> getTokenResponseForClientCredentials(
      String authorizationHeader, TokenRequest tokenRequest) {

    LOG.debug("Exchange token for 'client credentials' with [{}]", tokenRequest);

    ClientCredentials clientCredentials =
        TokenEndpointHelper.retrieveClientCredentials(authorizationHeader, tokenRequest);

    if (clientCredentials == null) {
      return TokenEndpointHelper.reportInvalidClientError();
    }

    Duration accessTokenLifetime = authorizationServerProperties.getAccessToken().getLifetime();
    Duration refreshTokenLifetime = authorizationServerProperties.getRefreshToken().getLifetime();

    RegisteredClient registeredClient;

    try {
      registeredClient =
          registeredClientAuthenticationService.authenticate(
              clientCredentials.getClientId(), clientCredentials.getClientSecret());

    } catch (AuthenticationException ex) {
      return TokenEndpointHelper.reportInvalidClientError();
    }

    if (registeredClient.getGrantTypes().contains(GrantType.CLIENT_CREDENTIALS)) {

      Set<String> scopes = new HashSet<>();
      if (StringUtils.isNotBlank(tokenRequest.getScope())) {
        scopes = new HashSet<>(Arrays.asList(tokenRequest.getScope().split(" ")));
      }

      LOG.info(
              "Creating token response for client credentials for client [{}]",
              tokenRequest.getClient_id());
      TokenResponse tokenResponse = new TokenResponse();
      String accessToken = AccessTokenFormat.JWT.equals(registeredClient.getAccessTokenFormat())
              ? tokenService
              .createAnonymousJwtAccessToken(
                      clientCredentials.getClientId(), scopes, accessTokenLifetime)
              .getValue()
              : tokenService
              .createAnonymousOpaqueAccessToken(
                      clientCredentials.getClientId(), scopes, accessTokenLifetime)
              .getValue();
      tokenResponse.setAccess_token(accessToken);
      tokenResponse.setToken_type(BEARER_TOKEN_TYPE);
      String refreshToken = tokenService
              .createAnonymousRefreshToken(
                      clientCredentials.getClientId(), scopes, refreshTokenLifetime)
              .getValue();
      long tokenLifeTime = accessTokenLifetime.toSeconds();
      tokenResponse.setRefresh_token(refreshToken);
      tokenResponse.setExpires_in(tokenLifeTime);
      return ResponseEntity.ok(tokenResponse);
    } else {
      return TokenEndpointHelper.reportUnauthorizedClientError();
    }
  }
}
