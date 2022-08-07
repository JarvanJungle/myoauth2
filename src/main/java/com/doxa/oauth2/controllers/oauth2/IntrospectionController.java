package com.doxa.oauth2.controllers.oauth2;

import com.doxa.oauth2.common.AuthenticationUtil;
import com.doxa.oauth2.common.ClientCredentials;
import com.doxa.oauth2.models.token.JsonWebToken;
import com.doxa.oauth2.models.token.OpaqueToken;
import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.requests.IntrospectionRequest;
import com.doxa.oauth2.responses.IntrospectionResponse;
import com.doxa.oauth2.serviceImpl.JsonWebTokenService;
import com.doxa.oauth2.serviceImpl.TokenService;
import com.doxa.oauth2.serviceImpl.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(IntrospectionController.ENDPOINT)
public class IntrospectionController {

    public static final String ENDPOINT = "/introspect";

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @PostMapping
    public ResponseEntity<IntrospectionResponse> introspect(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute("introspection_request") IntrospectionRequest introspectionRequest) {

        ClientCredentials clientCredentials;

        try {

            clientCredentials = AuthenticationUtil.fromBasicAuthHeader(authorizationHeader);
            if (clientCredentials == null) {
                return reportInvalidClientError();
            }

            String tokenValue = introspectionRequest.getToken();

            if (tokenValue == null || tokenValue.isBlank()) {
                return ResponseEntity.ok(new IntrospectionResponse(false));
            }

            JsonWebToken jsonWebToken = tokenService.findJsonWebToken(tokenValue);
            if (jsonWebToken != null) {
                return ResponseEntity.ok(getIntrospectionResponse(jsonWebToken));
            } else {
                OpaqueToken opaqueWebToken = tokenService.findOpaqueToken(tokenValue);
                if (opaqueWebToken != null) {
                    return ResponseEntity.ok(getIntrospectionResponse(opaqueWebToken));
                } else {
                    return ResponseEntity.ok(new IntrospectionResponse(false));
                }
            }
        } catch (BadCredentialsException ex) {
            return reportInvalidClientError();
        }
    }

    private IntrospectionResponse getIntrospectionResponse(OpaqueToken opaqueWebToken) {
        String clientId;
        Optional<User> user;
        try {
            opaqueWebToken.validate();
            clientId = opaqueWebToken.getClientId();
            if (TokenService.ANONYMOUS_TOKEN.equals(opaqueWebToken.getSubject())) {

                IntrospectionResponse introspectionResponse = new IntrospectionResponse();
                introspectionResponse.setScope("SCOPE_users:read,SCOPE_users:write");
                introspectionResponse.setActive(true);
                introspectionResponse.setClient_id(clientId);
                introspectionResponse.setSub(TokenService.ANONYMOUS_TOKEN);
                introspectionResponse.setUsername(TokenService.ANONYMOUS_TOKEN);
                introspectionResponse.setExp(opaqueWebToken.getExpiry().atZone(ZoneId.systemDefault()).toEpochSecond());
                introspectionResponse.setIss(opaqueWebToken.getIssuer());
                introspectionResponse.setNbf(opaqueWebToken.getNotBefore().atZone(ZoneId.systemDefault()).toEpochSecond());
                introspectionResponse.setIat(opaqueWebToken.getIssuedAt().atZone(ZoneId.systemDefault()).toEpochSecond());
                return introspectionResponse;
            } else {
                user = userService.findById(opaqueWebToken.getSubject());
                return user.map(
                        u -> {
                            IntrospectionResponse introspectionResponse = new IntrospectionResponse();
                            introspectionResponse.setActive(true);
                            introspectionResponse.setClient_id(clientId);
                            introspectionResponse.setSub(u.getId().toString());
                            introspectionResponse.setUsername(u.getName());
                            introspectionResponse.setExp(opaqueWebToken.getExpiry().atZone(ZoneId.systemDefault()).toEpochSecond());
                            introspectionResponse.setIss(opaqueWebToken.getIssuer());
                            introspectionResponse.setNbf(opaqueWebToken.getNotBefore().atZone(ZoneId.systemDefault()).toEpochSecond());
                            introspectionResponse.setIat(opaqueWebToken.getIssuedAt().atZone(ZoneId.systemDefault()).toEpochSecond());
                            introspectionResponse.setScope("SCOPE_users:read,SCOPE_users:write");
                            return introspectionResponse;
                        })
                        .orElse(new IntrospectionResponse(false));
            }
        } catch (BadCredentialsException ex) {
            return new IntrospectionResponse(false);
        }
    }

    private IntrospectionResponse getIntrospectionResponse(JsonWebToken jsonWebToken) {
        String clientId;
        String ctx;

        try {
            JWTClaimsSet jwtClaimsSet =
                    jsonWebTokenService.parseAndValidateToken(jsonWebToken.getValue());
            clientId = jwtClaimsSet.getStringClaim("client_id");
            ctx = jwtClaimsSet.getStringClaim("ctx");
            String subject = jwtClaimsSet.getSubject();

            if (TokenService.ANONYMOUS_TOKEN.equals(ctx)) {
                IntrospectionResponse introspectionResponse = new IntrospectionResponse();
                introspectionResponse.setActive(true);
                introspectionResponse.setClient_id(clientId);
                introspectionResponse.setSub(clientId);
                introspectionResponse.setUsername(clientId);
                introspectionResponse.setIss(jwtClaimsSet.getIssuer());
                introspectionResponse.setNbf(jwtClaimsSet.getNotBeforeTime().getTime());
                introspectionResponse.setIat(jwtClaimsSet.getIssueTime().getTime());
                introspectionResponse.setExp(jwtClaimsSet.getExpirationTime().getTime());
                return introspectionResponse;
            } else {
                Optional<User> user = userService.findById(subject);
                return user.map(
                        u -> {
                            IntrospectionResponse introspectionResponse = new IntrospectionResponse();
                            introspectionResponse.setActive(true);
                            introspectionResponse.setClient_id(clientId);
                            introspectionResponse.setAud(new ArrayList<>(){{add(clientId);}});
                            introspectionResponse.setSub(u.getId().toString());
                            introspectionResponse.setUsername(u.getName());
                            introspectionResponse.setIss(jwtClaimsSet.getIssuer());
                            introspectionResponse.setNbf(jwtClaimsSet.getNotBeforeTime().getTime());
                            introspectionResponse.setIat(jwtClaimsSet.getIssueTime().getTime());
                            introspectionResponse.setExp(jwtClaimsSet.getExpirationTime().getTime());
                            introspectionResponse.setScope("SCOPE_users:read,SCOPE_users:write");
                            return introspectionResponse;
                        })
                        .orElse(new IntrospectionResponse(false));
            }
        } catch (ParseException | JOSEException e) {
            return new IntrospectionResponse(false);
        }
    }

    private ResponseEntity<IntrospectionResponse> reportInvalidClientError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic")
                .body(new IntrospectionResponse("invalid_client"));
    }
}
