package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.components.JwtPki;
import com.doxa.oauth2.models.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class JsonWebTokenService {

    private static final JOSEObjectType JWT_TYP_ACCESS_TOKEN = new JOSEObjectType("jwt");

    @Autowired
    private JwtPki jwtPki;
    @Autowired
    private IdGenerator idGenerator;

//    public JsonWebTokenService(JwtPki jwtPki, IdGenerator idGenerator) {
//        this.jwtPki = jwtPki;
//        this.idGenerator = idGenerator;
//    }

    @Autowired
    private AuthorityService authorityService;

    public JWTClaimsSet parseAndValidateToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        signedJWT.verify(jwtPki.getVerifier());
        return signedJWT.getJWTClaimsSet();
    }

    public String createPersonalizedToken(
            boolean isAccessToken,
            String clientId,
            List<String> audiences,
            Set<String> scopes,
            User user,
//            String nonce,
            LocalDateTime expiryDateTime)
            throws JOSEException {


        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
//                .subject(user.getId().toString())
                .issuer(jwtPki.getIssuer())
                .expirationTime(Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .audience(audiences)
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .jwtID(idGenerator.generateId().toString())
//                .claim("nonce", nonce)
                .claim("name", user.getName())
                .claim("user_id", user.getId())
//                .claim("designation", user.getDesignation())
                .claim("client_id", clientId)
//                .claim("locale", user.getSettings().getLanguage().getLanguageCode())
                .claim("ctx", TokenService.PERSONAL_TOKEN);

//        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//            builder.claim("roles", user.getRoles());
//        }
        builder.claim("companies", authorityService.getAuthorities(user));

        JWTClaimsSet claimsSet = builder.build();

        SignedJWT signedJWT = createSignedJWT(isAccessToken, claimsSet);
        signedJWT.sign(jwtPki.getSigner());

        return signedJWT.serialize();
    }

    public String createAnonymousToken(
            boolean isAccessToken,
            String clientId,
            List<String> audiences,
            Set<String> scopes,
            LocalDateTime expiryDateTime)
            throws JOSEException {
        JWTClaimsSet.Builder builder =
                new JWTClaimsSet.Builder()
                        .subject(clientId)
                        .issuer(jwtPki.getIssuer())
                        .expirationTime(Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                        .audience(audiences)
                        .issueTime(new Date())
                        .notBeforeTime(new Date())
                        .jwtID(idGenerator.generateId().toString())
                        .claim("client_id", clientId)
                        .claim("ctx", TokenService.ANONYMOUS_TOKEN);

        if (!scopes.isEmpty()) {
            builder.claim("scope", String.join(" ", scopes));
        }

        JWTClaimsSet claimsSet = builder.build();

        SignedJWT signedJWT = createSignedJWT(isAccessToken, claimsSet);
        signedJWT.sign(jwtPki.getSigner());

        return signedJWT.serialize();
    }

    private SignedJWT createSignedJWT(boolean isAccessToken, JWTClaimsSet claimsSet) {
        JWSHeader.Builder jwsHeaderBuilder = new JWSHeader.Builder(JWSAlgorithm.RS256);
        jwsHeaderBuilder.keyID(jwtPki.getJwkKid());

        if (isAccessToken) {
            jwsHeaderBuilder.type(JWT_TYP_ACCESS_TOKEN);
        }

        return new SignedJWT(
                jwsHeaderBuilder.build(),
                claimsSet);
    }

    public boolean isSignatureValid(String jwtToken) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(jwtToken);
            JWSVerifier verifier = new RSASSAVerifier(this.jwtPki.getPublicKey());
            return signedJWT.verify(verifier);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }
}
