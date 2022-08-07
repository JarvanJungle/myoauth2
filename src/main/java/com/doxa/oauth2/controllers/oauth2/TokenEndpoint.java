package com.doxa.oauth2.controllers.oauth2;

import com.doxa.oauth2.common.GrantType;
import com.doxa.oauth2.requests.TokenRequest;
import com.doxa.oauth2.responses.TokenResponse;
import com.doxa.oauth2.serviceImpl.AuthorizationCodeTokenEndpointService;
import com.doxa.oauth2.serviceImpl.ClientCredentialsTokenEndpointService;
import com.doxa.oauth2.serviceImpl.PasswordTokenEndpointService;
import com.doxa.oauth2.serviceImpl.RefreshTokenEndpointService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
//@RequestMapping(TokenEndpoint.ENDPOINT)
@RestController
public class TokenEndpoint {
  public static final String ENDPOINT = "/token";
  private static final Logger LOG = LoggerFactory.getLogger(TokenEndpoint.class);

  private final ClientCredentialsTokenEndpointService clientCredentialsTokenEndpointService;
  private final PasswordTokenEndpointService passwordTokenEndpointService;
  private final RefreshTokenEndpointService refreshTokenEndpointService;
  private final AuthorizationCodeTokenEndpointService authorizationCodeTokenEndpointService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public TokenEndpoint(
          ClientCredentialsTokenEndpointService clientCredentialsTokenEndpointService,
          PasswordTokenEndpointService passwordTokenEndpointService,
          RefreshTokenEndpointService refreshTokenEndpointService,
          AuthorizationCodeTokenEndpointService authorizationCodeTokenEndpointService) {
    this.clientCredentialsTokenEndpointService = clientCredentialsTokenEndpointService;
    this.passwordTokenEndpointService = passwordTokenEndpointService;
    this.refreshTokenEndpointService = refreshTokenEndpointService;
    this.authorizationCodeTokenEndpointService = authorizationCodeTokenEndpointService;
  }

  @PostMapping(path = ENDPOINT, consumes = ("application/x-www-form-urlencoded"))
  public ResponseEntity<TokenResponse> getToken(
          @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
          @ModelAttribute TokenRequest tokenRequest, HttpServletResponse response) {

    LOG.info("Exchange token with grant type [{}]", tokenRequest.getGrant_type());

    if (tokenRequest.getGrant_type().equalsIgnoreCase(GrantType.CLIENT_CREDENTIALS.getGrant())) {
      return clientCredentialsTokenEndpointService.getTokenResponseForClientCredentials(
              authorizationHeader, tokenRequest);
    } else if (tokenRequest.getGrant_type().equalsIgnoreCase(GrantType.PASSWORD.getGrant())) {
      return passwordTokenEndpointService.getTokenResponseForPassword(
              authorizationHeader, tokenRequest);
    } else if (tokenRequest
            .getGrant_type()
            .equalsIgnoreCase(GrantType.AUTHORIZATION_CODE.getGrant())) {
      Cookie cookie = new Cookie("auth.access_token", "token");
      response.addCookie(cookie);
      return authorizationCodeTokenEndpointService.getTokenResponseForAuthorizationCode(
              authorizationHeader, tokenRequest);
    } else if (tokenRequest.getGrant_type().equalsIgnoreCase(GrantType.REFRESH_TOKEN.getGrant())) {
      return refreshTokenEndpointService.getTokenResponseForRefreshToken(
              authorizationHeader, tokenRequest);
    } else if (tokenRequest.getGrant_type().equalsIgnoreCase(GrantType.TOKEN_EXCHANGE.getGrant())) {
      LOG.warn("Requested grant type for 'Token Exchange' is not yet supported");
      return ResponseEntity.badRequest().body(new TokenResponse("unsupported_grant_type"));
    } else {
      LOG.warn("Requested grant type [{}] is unsupported", tokenRequest.getGrant_type());
      return ResponseEntity.badRequest().body(new TokenResponse("unsupported_grant_type"));
    }
  }

  @PostMapping(value = "/check_token", consumes = ("application/x-www-form-urlencoded"))
  public ResponseEntity<String> getToken(@ModelAttribute Object data){
    return ResponseEntity.ok("test");
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> handle(MissingServletRequestParameterException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handle(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  @ExceptionHandler(JOSEException.class)
  public ResponseEntity<String> handle(JOSEException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }
}
