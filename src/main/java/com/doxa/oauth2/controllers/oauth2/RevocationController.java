package com.doxa.oauth2.controllers.oauth2;

import com.doxa.oauth2.common.AuthenticationUtil;
import com.doxa.oauth2.common.ClientCredentials;
import com.doxa.oauth2.models.token.JsonWebToken;
import com.doxa.oauth2.models.token.OpaqueToken;
import com.doxa.oauth2.requests.RevocationRequest;
import com.doxa.oauth2.responses.RevocationResponse;
import com.doxa.oauth2.security.client.RegisteredClientAuthenticationService;
import com.doxa.oauth2.serviceImpl.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/** OAuth 2.0 Token Revocation as specified in https://tools.ietf.org/html/rfc7009 */
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(RevocationController.ENDPOINT)
public class RevocationController {

  public static final String ENDPOINT = "/revoke";

  private static final Logger LOG = LoggerFactory.getLogger(RevocationController.class);

  private final TokenService tokenService;
  private final RegisteredClientAuthenticationService registeredClientAuthenticationService;

  public RevocationController(
      TokenService tokenService,
      RegisteredClientAuthenticationService registeredClientAuthenticationService) {
    this.tokenService = tokenService;
    this.registeredClientAuthenticationService = registeredClientAuthenticationService;
  }

  /**
   * Revocation Request. The client constructs the request by including the following parameters
   * using the "application/x-www-form-urlencoded" format in the HTTP request entity-body.
   *
   * @param authorizationHeader the authorization header for authenticating the client
   * @param revocationRequest Revocation Request
   * @param result Validation result of request parameters
   * @return the Revocation Response as specified in rfc7009
   */
  @PostMapping
  public ResponseEntity<RevocationResponse> revoke(
      @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
      @ModelAttribute("revocation_request") RevocationRequest revocationRequest,
      BindingResult result) {

    ClientCredentials clientCredentials;

    try {

      clientCredentials = AuthenticationUtil.fromBasicAuthHeader(authorizationHeader);
      if (clientCredentials != null) {
        try {
          registeredClientAuthenticationService.authenticate(
              clientCredentials.getClientId(), clientCredentials.getClientSecret());
        } catch (AuthenticationException ex) {
          return ResponseEntity.status(UNAUTHORIZED).header("WWW-Authenticate", "Basic")
              .body(new RevocationResponse(null, "invalid_client"));
        }
      }

      /*
       A hint about the type of the token
          submitted for revocation.  Clients MAY pass this parameter in
          order to help the authorization server to optimize the token
          lookup.  If the server is unable to locate the token using
          the given hint, it MUST extend its search across all of its
          supported token types.  An authorization server MAY ignore
          this parameter, particularly if it is able to detect the
          token type automatically.
      */

      LOG.debug("Revocation request [{}]", revocationRequest);

      OpaqueToken opaqueWebToken = tokenService.findOpaqueToken(revocationRequest.getToken());
      if (opaqueWebToken != null) {
        tokenService.remove(opaqueWebToken);
        LOG.info("[{}] token (Opaque) has been revoked", opaqueWebToken.isRefreshToken() ? "Refresh" : "Access");
      } else {
        JsonWebToken jsonWebToken = tokenService.findJsonWebAccessToken(revocationRequest.getToken());
        if (jsonWebToken != null) {
          tokenService.remove(jsonWebToken);
          LOG.info("Access token (JWT) has been revoked");
        } else {
          return ResponseEntity.badRequest().body(new RevocationResponse(null, "invalid_request"));
        }
      }
      return ResponseEntity.ok(new RevocationResponse("ok", null));
    } catch (BadCredentialsException ex) {
      return reportInvalidClientError();
    }
  }

  private ResponseEntity<RevocationResponse> reportInvalidClientError() {
    return ResponseEntity.status(UNAUTHORIZED)
        .header("WWW-Authenticate", "Basic")
        .body(new RevocationResponse(null, "invalid_client"));
  }
}
