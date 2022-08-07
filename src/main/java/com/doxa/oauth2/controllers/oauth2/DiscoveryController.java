package com.doxa.oauth2.controllers.oauth2;


import com.doxa.oauth2.common.GrantType;
import com.doxa.oauth2.common.Scope;
import com.doxa.oauth2.components.JwtPki;
import com.doxa.oauth2.responses.Discovery;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(DiscoveryController.ENDPOINT)
public class DiscoveryController {

  public static final String ENDPOINT = "/.well-known/openid-configuration";

  private final JwtPki jwtPki;

  public DiscoveryController(JwtPki jwtPki) {
    this.jwtPki = jwtPki;
  }

  @GetMapping
  public Discovery discoveryEndpoint() {

    Discovery discovery = new Discovery();
    discovery.setAuthorization_endpoint(jwtPki.getIssuer() + AuthorizationController.ENDPOINT);
    discovery.setIssuer(jwtPki.getIssuer());
    discovery.setToken_endpoint(jwtPki.getIssuer() + TokenEndpoint.ENDPOINT);
    discovery.setIntrospection_endpoint(jwtPki.getIssuer() + IntrospectionController.ENDPOINT);
    discovery.setRevocation_endpoint(jwtPki.getIssuer() + RevocationController.ENDPOINT);
    discovery.setUserinfo_endpoint(jwtPki.getIssuer() + "/api/users/me");
    discovery.setJwks_uri(jwtPki.getIssuer() + "/jwks");
    discovery.getGrant_types_supported().add(GrantType.AUTHORIZATION_CODE.getGrant());
    discovery.getGrant_types_supported().add(GrantType.CLIENT_CREDENTIALS.getGrant());
    discovery.getGrant_types_supported().add(GrantType.PASSWORD.getGrant());
    discovery.getGrant_types_supported().add(GrantType.TOKEN_EXCHANGE.getGrant());
    discovery.getResponse_types_supported().add("code");
    discovery.getScopes_supported().add(Scope.OPENID.name().toLowerCase());
    discovery.getScopes_supported().add(Scope.OFFLINE_ACCESS.name().toLowerCase());
    discovery.getScopes_supported().add(Scope.PROFILE.name().toLowerCase());
    discovery.getScopes_supported().add(Scope.EMAIL.name().toLowerCase());
    discovery.getScopes_supported().add(Scope.PHONE.name().toLowerCase());
    discovery.getScopes_supported().add(Scope.ADDRESS.name().toLowerCase());
    discovery.getResponse_modes_supported().add("query");
    discovery.getResponse_modes_supported().add("form_post");
    discovery.getSubject_types_supported().add("public");
    discovery.getId_token_signing_alg_values_supported().add("RS256");
    discovery.getToken_endpoint_auth_methods_supported().add("client_secret_basic");
    discovery.getToken_endpoint_auth_methods_supported().add("client_secret_post");
    discovery.getCode_challenge_methods_supported().add("S256");
    discovery.getCode_challenge_methods_supported().add("plain");
    discovery.getClaims_supported().add("aud");
    discovery.getClaims_supported().add("auth_time");
    discovery.getClaims_supported().add("created_at");
    discovery.getClaims_supported().add("gender");
    discovery.getClaims_supported().add("birthdate");
    discovery.getClaims_supported().add("locale");
    discovery.getClaims_supported().add("zoneinfo");
    discovery.getClaims_supported().add("address");
    discovery.getClaims_supported().add("email");
    discovery.getClaims_supported().add("email_verified");
    discovery.getClaims_supported().add("exp");
    discovery.getClaims_supported().add("website");
    discovery.getClaims_supported().add("picture");
    discovery.getClaims_supported().add("family_name");
    discovery.getClaims_supported().add("given_name");
    discovery.getClaims_supported().add("iat");
    discovery.getClaims_supported().add("identities");
    discovery.getClaims_supported().add("iss");
    discovery.getClaims_supported().add("identities");
    discovery.getClaims_supported().add("name");
    discovery.getClaims_supported().add("nickname");
    discovery.getClaims_supported().add("phone_number");
    discovery.getClaims_supported().add("phone_number_verified");
    discovery.getClaims_supported().add("sub");
    discovery.getToken_endpoint_auth_signing_alg_values_supported().add("RS256");

    return discovery;
  }
}
